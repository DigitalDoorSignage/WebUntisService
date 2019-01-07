package at.htl;

import at.htl.webuntis.entity.*;
import at.htl.webuntis.WebUntisClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebUntisService {
    private Long lastImportTime;
    private WebUntisClient webUntisClient;
    private MqttClient mqttClient;
    // this service is responsible for checking the webuntis api for updates and calls the #loadTimetables function if there are any
    private ScheduledExecutorService updateCheckerService;
    // this service is responsible for publishing a message under the right topic when the lesson changes
    private ScheduledExecutorService lessonNotifierService;
    private List<RoomTimetable> roomTimetables;

    public WebUntisService(String mqttServer) throws MqttException {
        lastImportTime = null;
        webUntisClient = new WebUntisClient("mese", "htbla linz leonding", "if150152", "teamtengu1");
        mqttClient = new MqttClient(mqttServer, MqttClient.generateClientId(), null);
        updateCheckerService = Executors.newScheduledThreadPool(1);
        lessonNotifierService = Executors.newScheduledThreadPool(1);
        webUntisClient.login();
        mqttClient.connect();
    }

    public void loadTimetables(){
        roomTimetables = webUntisClient
                .getAllRooms()
                .stream()
                .map(r -> webUntisClient.getTimetableOfRoom(r, getCurrentLocalDateTime().toLocalDate()))
                .collect(Collectors.toList());
    }

    public void start(){
        updateCheckerService.scheduleAtFixedRate(this::checkForUpdate, 0, 2, TimeUnit.MINUTES);
        lessonNotifierService.scheduleAtFixedRate(this::notifyLessons, 0, 1, TimeUnit.MINUTES);
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
            for(RoomTimetable roomTimetable : roomTimetables){
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
                    Teacher teacher = lesson.getTeacher();
                    Klass klass = lesson.getKlass();

                    messageContent = new ObjectMapper().createObjectNode()
                            .put("subject", subject == null? "-" : subject.getName())
                            .put("teacher", teacher == null? "-" : teacher.getShortName())
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
