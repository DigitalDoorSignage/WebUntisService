package at.htl.webuntis.entity;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTimetableTest {
    @org.junit.jupiter.api.Test
    void equals() throws IOException {
        RoomTimetable table1 = new ObjectMapper().readValue(Paths.get("roomtimetable.json").toFile(), RoomTimetable.class);

        RoomTimetable table2 = new ObjectMapper().readValue(Paths.get("roomtimetable.json").toFile(), RoomTimetable.class);

        assertEquals(table1, table2);
    }
}