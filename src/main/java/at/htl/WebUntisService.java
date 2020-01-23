package at.htl;

import at.htl.webuntis.cache.RoomTimetableCache;
import at.htl.webuntis.WebUntisClient;
import at.htl.webuntis.entity.RoomTimetable;
import org.yaml.snakeyaml.Yaml;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebUntisService {
    private Long lastImportTime;
    private WebUntisClient webUntisClient;
    // this service is responsible for checking the webuntis api for updates and calls the loadTimetables function if there are any
    private ScheduledExecutorService updateCheckerService;
    RoomTimetableCache cache = RoomTimetableCache.getInstance();

    public WebUntisService() {
        lastImportTime = null;

        updateCheckerService = Executors.newScheduledThreadPool(1);

    }

    public void loadTimetables(){
        webUntisClient
                 .getAllRooms()
                 .stream()
                 .map(r -> webUntisClient.getTimetableOfRoom(r, getCurrentLocalDateTime().toLocalDate()))
                 .forEach(r -> {
                     String name = r.getRoom().getLongName();
                     cache.set(name, r);
                 });
    }

    public void start() throws URISyntaxException, FileNotFoundException {
        String configUrl = getClass().getResource("/config.yaml").toString().replace("vfs:/", "file:/");

        Map<String, String> config = new Yaml().load(
                new FileInputStream(new File(new URI(configUrl)))
        );

        webUntisClient = new WebUntisClient(
                "mese",
                "htbla linz leonding",
                (String) config.get("username"),
                (String) config.get("password")
        );
        webUntisClient.login();
        updateCheckerService.scheduleAtFixedRate(this::checkForUpdate, 0, Integer.parseInt(config.getOrDefault("delay", "30")), TimeUnit.SECONDS);
    }

    private void checkForUpdate(){
        try{
            this.cache.getItems().forEach(item -> {
                RoomTimetable rt = item.getValue();
                if(rt.updateCurrentLesson()){
                    item.updated();
                }
            });
            Long latestImportTime = webUntisClient.getLatestImportTime();
            if(lastImportTime == null || lastImportTime < latestImportTime){
                loadTimetables();
                lastImportTime = latestImportTime;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private LocalDateTime getCurrentLocalDateTime(){
        return LocalDateTime.now();
    }
}
