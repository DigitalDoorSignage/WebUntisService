package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomTimetable {
    private Room room;
    private List<Teacher> teachers;
    private List<Subject> subjects;
    private List<Klass> klasses;
    private Map<String, List<Lesson>> lessonsPerDay;
    private Long lastImportTimestamp;

    public RoomTimetable(Room room, List<Teacher> teachers, List<Subject> subjects, List<Klass> klasses, Map<String, List<Lesson>> lessonsPerDay, Long lastImportTimestamp) {
        this.room = room;
        this.teachers = teachers;
        this.subjects = subjects;
        this.klasses = klasses;
        this.lessonsPerDay = lessonsPerDay;
        this.lastImportTimestamp = lastImportTimestamp;
    }

    public static RoomTimetable parse(Room room, JsonNode body) {
        JsonNode result = body.get("data").get("result");
        JsonNode data = result.get("data");
        JsonNode elementPeriods = data.get("elementPeriods").get(Integer.toString(room.getId()));
        JsonNode elements = data.get("elements");

        Long lastImportTimestamp = result.get("lastImportTimestamp").asLong();
        List<Teacher> teachers = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();
        List<Klass> klasses = new ArrayList<>();
        Map<String, List<Lesson>> lessonsPerDay = new HashMap<>();

        for (JsonNode jsonNode : elements) {
            int type = jsonNode.get("type").asInt();
            switch(type){
                case Klass.TYPE:
                    klasses.add(Klass.parse(jsonNode));
                    break;
                case Teacher.TYPE:
                    teachers.add(Teacher.parse(jsonNode));
                    break;
                case Subject.TYPE:
                    subjects.add(Subject.parse(jsonNode));
                    break;
            }
        }

        for(JsonNode jsonNode : elementPeriods){
            Lesson lesson = Lesson.parse(jsonNode, room, teachers, subjects, klasses);
            if(!lessonsPerDay.containsKey(lesson.getDate()))
                lessonsPerDay.put(lesson.getDate(), new ArrayList<Lesson>());
            lessonsPerDay.get(lesson.getDate()).add(lesson);
        }

        return new RoomTimetable(room, teachers, subjects, klasses, lessonsPerDay, lastImportTimestamp);
    }

    public Room getRoom() {
        return room;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Klass> getKlasses() {
        return klasses;
    }

    public Long getLastImportTimestamp() {
        return lastImportTimestamp;
    }

    public Map<String, List<Lesson>> getLessonsPerDay() {
        return lessonsPerDay;
    }
}
