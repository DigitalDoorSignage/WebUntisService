package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lesson {
    private int id;
    private String date;
    private int startTime;
    private int endTime;
    private Room room;
    private List<Teacher> teachers;
    private Subject subject;
    private Klass klass;

    public Lesson(int id, String date, int startTime, int endTime, Room room, List<Teacher> teachers, Subject subject, Klass klass) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.teachers = teachers;
        this.subject = subject;
        this.klass = klass;
    }

    public Lesson() {
    }

    public static Lesson parse(JsonNode jsonNode, Room room, List<Room> rooms, List<Teacher> teachers, List<Subject> subjects, List<Klass> klasses) {
        JsonNode elements = jsonNode.get("elements");
        List<Teacher> lessonTeachers = new ArrayList<>();
        Subject subject = null;
        Klass klass = null;
        for(JsonNode e : elements){
            int id = e.get("id").asInt();

            switch(e.get("type").asInt()){
                case Teacher.TYPE:
                    lessonTeachers.add(teachers.stream().filter(x ->  x.getId() == id).findFirst().orElse(null));
                    break;
                case Subject.TYPE:
                    subject = subjects.stream().filter(x ->  x.getId() == id).findFirst().orElse(null);
                    break;
                case Klass.TYPE:
                    klass = klasses.stream().filter(x ->  x.getId() == id).findFirst().orElse(null);
                    break;
            }
        }

        return new Lesson(
                jsonNode.get("id").asInt(),
                jsonNode.get("date").asText(),
                jsonNode.get("startTime").asInt(),
                jsonNode.get("endTime").asInt(),
                room,
                lessonTeachers,
                subject,
                klass
        );
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public Room getRoom() {
        return room;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public Subject getSubject() {
        return subject;
    }

    public Klass getKlass() {
        return klass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id &&
                startTime == lesson.startTime &&
                endTime == lesson.endTime &&
                Objects.equals(date, lesson.date) &&
                Objects.equals(room, lesson.room) &&
                hasSameTeachers(lesson.teachers) &&
                Objects.equals(subject, lesson.subject) &&
                Objects.equals(klass, lesson.klass);
    }

    private boolean hasSameTeachers(List<Teacher> others){
        if(teachers.size() != others.size())
            return false;

        for(int i = 0; i < teachers.size(); i++){
            if(!teachers.get(i).equals(others.get(i)))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, startTime, endTime, room, teachers, subject, klass);
    }
}
