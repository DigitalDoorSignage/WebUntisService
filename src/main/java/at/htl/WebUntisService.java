package at.htl;

import at.htl.webuntis.entity.*;
import at.htl.webuntis.WebUntisClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebUntisService {
    private Long lastImportTime;
    private WebUntisClient webUntisClient;
    private MqttClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;
    // this service is responsible for checking the webuntis api for updates and calls the #loadTimetables function if there are any
    private ScheduledExecutorService updateCheckerService;
    // this service is responsible for publishing a message under the right topic when the lesson changes
    private ScheduledExecutorService lessonNotifierService;
    // this map holds all current roomtimetables
    private Map<String, RoomTimetable> roomTimetables;
    // this list holds the roomtimetables that got changed with the latest updates
    private List<RoomTimetable> roomTimetablesDiff;

    public WebUntisService(String mqttServer) throws MqttException, FileNotFoundException {
        lastImportTime = null;
        Map<String, Object> config = new Yaml().load(
                new FileInputStream(Paths.get("config.yaml").toFile())
        );
        webUntisClient = new WebUntisClient(
                "mese",
                "htbla linz leonding",
                (String) config.get("username"),
                (String) config.get("password")
        );
        webUntisClient.login();

        mqttClient = new MqttClient(mqttServer, MqttClient.generateClientId(), null);
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttClient.connect(mqttConnectOptions);

        updateCheckerService = Executors.newScheduledThreadPool(1);
        lessonNotifierService = Executors.newScheduledThreadPool(1);
    }

    public void loadTimetables(){
        Map<String, RoomTimetable> newRoomTimetables = new HashMap<>();
        this.roomTimetablesDiff = new ArrayList<>();

        webUntisClient
                 .getAllRooms()
                 .stream()
                 .map(r -> webUntisClient.getTimetableOfRoom(r, getCurrentLocalDateTime().toLocalDate()))
                 .forEach(r -> {
                     String name = r.getRoom().getLongName();
                     newRoomTimetables.put(name, r);
                     if(this.roomTimetables == null || !r.equals(this.roomTimetables.get(name))){
                         this.roomTimetablesDiff.add(r);
                     }
                 });

        this.roomTimetables = newRoomTimetables;
    }

    public void start(){
        updateCheckerService.scheduleAtFixedRate(this::checkForUpdate, 0, 2, TimeUnit.MINUTES);
        lessonNotifierService.scheduleAtFixedRate(this::notifyLessons, 0, 20, TimeUnit.SECONDS);
    }

    private void checkForUpdate(){
        try{
            Long latestImportTime = webUntisClient.getLatestImportTime();
            if(lastImportTime == null || lastImportTime < latestImportTime){
                System.out.println("have to work");
                loadTimetables();
                lastImportTime = latestImportTime;
            }
            else {
                System.out.println("dont have to work");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void notifyLessons(){
        try{
            if(roomTimetables == null)
                return;
            LocalDateTime now = getCurrentLocalDateTime();
            String dateString = now.format(DateTimeFormatter.ofPattern("YYYYMMdd"));
            int time = now.getHour() * 100 + now.getMinute();
            for(RoomTimetable roomTimetable : roomTimetablesDiff){
                List<Lesson> lessons = roomTimetable
                        .getLessonsPerDay()
                        .get(dateString);

                if(lessons == null)
                    continue;

                Lesson lesson = lessons
                        .stream()
                        .filter(x -> x.getStartTime() <= time && time <= x.getEndTime())
                        .findFirst()
                        .orElse(null);

                Room room = roomTimetable.getRoom();

                if(room == null)
                    continue;

                JsonNode messageContent;

                if(lesson == null){
                    messageContent = new ObjectMapper().createObjectNode()
                            .put("subject", "-")
                            .put("teacher", "-")
                            .put("class", "-")
                            .put("room", room.getLongName());
                }
                else {
                    Subject subject = lesson.getSubject();
                    List<Teacher> teachers = lesson.getTeachers();
                    Klass klass = lesson.getKlass();

                    messageContent = new ObjectMapper().createObjectNode()
                            .put("subject", subject == null? "-" : subject.getName())
                            .put("teacher", teachers.size() == 0? "-" : teachers.stream().map(t -> t.getShortName()).collect(Collectors.joining(", ")))
                            .put("class", klass == null? "-" : klass.getName())
                            .put("room", room.getLongName());
                }


                MqttMessage mqttMessage = new MqttMessage(messageContent.toString().getBytes());
                mqttClient.publish("room/" + room.getLongName() + "/command", mqttMessage);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private LocalDateTime getCurrentLocalDateTime(){
        return LocalDateTime.now();
    }
}
