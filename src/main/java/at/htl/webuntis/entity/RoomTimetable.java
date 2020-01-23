package at.htl.webuntis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbTransient;
import java.time.LocalDateTime;
import java.util.*;

public class RoomTimetable {
    public static String fakeDate;
    public static Integer fakeTime;
    private Room room;
    private Lesson currentLesson;
    @JsonIgnore
    private List<Teacher> teachers;
    @JsonIgnore
    private List<Subject> subjects;
    @JsonIgnore
    private List<Klass> klasses;
    private Map<String, List<Lesson>> lessonsPerDay;
    @JsonIgnore
    private Long lastImportTimestamp;

    public RoomTimetable(Room room, List<Teacher> teachers, List<Subject> subjects, List<Klass> klasses, Map<String, List<Lesson>> lessonsPerDay, Long lastImportTimestamp) {
        this.room = room;
        this.teachers = teachers;
        this.subjects = subjects;
        this.klasses = klasses;
        this.lessonsPerDay = lessonsPerDay;
        this.lastImportTimestamp = lastImportTimestamp;
    }

    public RoomTimetable(){

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
        List<Room> rooms = new ArrayList<>();
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
                case Room.TYPE:
                    rooms.add(Room.parse(jsonNode));
                    break;
            }
        }



        for(JsonNode jsonNode : elementPeriods){
            Lesson lesson = Lesson.parse(jsonNode, room, rooms, teachers, subjects, klasses);
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

    boolean lessonsPerDayEquals(Map<String, List<Lesson>> other){
        for(Map.Entry<String, List<Lesson>> lessonsPerDay : this.lessonsPerDay.entrySet()){
            String date = lessonsPerDay.getKey();
            List<Lesson> lessons = lessonsPerDay.getValue();
            List<Lesson> otherLessons = other.get(date);

            if(lessons.size() != otherLessons.size())
                return false;

            for (int i = 0; i < lessons.size(); i++) {
                if(!lessons.get(i).equals(otherLessons.get(i)))
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTimetable that = (RoomTimetable) o;
        return Objects.equals(room, that.room) &&
                lessonsPerDayEquals(that.lessonsPerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, lessonsPerDay);
    }

    public String toJson(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateCurrentLesson(){
        int time = getCurrentTime();
        String currentDate = getCurrentDate();

        List<Lesson> lessons = this.lessonsPerDay.get(currentDate);

        if(lessons == null)
            return false;

        if(currentLesson != null) {
            int endTime = currentLesson.getEndTime();
            int startTime = currentLesson.getStartTime();

            if(!currentLesson.getDate().equals(currentDate) || endTime < time  || startTime > time){
                this.currentLesson = getLessonAtTime(lessons, time);
            }
            else {
                return false;
            }
        }
        else {
            this.currentLesson = getLessonAtTime(lessons, time);
        }

        return true;
    }

    private Lesson getLessonAtTime(List<Lesson> lessons, int time) {
        for (Lesson l : lessons) {
            if(l.getStartTime() <= time && time <= l.getEndTime()){
                return l;
            }
        }
        return null;
    }

    private int getCurrentTime(){
        if(fakeTime != null){
            return fakeTime;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() * 100 + now.getMinute();
    }
    private String getCurrentDate(){
        if(fakeDate != null){
            return fakeDate;
        }
        LocalDateTime now = LocalDateTime.now();
        return String.format("%04d%02d%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    public Lesson getCurrentLesson() {
        return this.currentLesson;
    }
}
