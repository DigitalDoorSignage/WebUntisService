package at.htl.webuntis.cache;

import at.htl.webuntis.entity.RoomTimetable;

import javax.inject.Singleton;
import java.util.Optional;

public class RoomTimetableCache extends MemCache<String, RoomTimetable> {
    private static RoomTimetableCache instance;

    public static RoomTimetableCache getInstance() {
        if(instance == null)
            instance = new RoomTimetableCache();
        return instance;
    }

    private RoomTimetableCache(){

    }
}
