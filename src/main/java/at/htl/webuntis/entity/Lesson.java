package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class Lesson {
    private int id;
    private String date;
    private int startTime;
    private int endTime;
    private Room room;
    private Teacher teacher;
    private Subject subject;
    private Klass klass;

    public Lesson(int id, String date, int startTime, int endTime, Room room, Teacher teacher, Subject subject, Klass klass) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.teacher = teacher;
        this.subject = subject;
        this.klass = klass;
    }

    public static Lesson parse(JsonNode jsonNode, Room room, List<Teacher> teachers, List<Subject> subjects, List<Klass> klasses) {
        JsonNode elements = jsonNode.get("elements");
        Teacher teacher = null;
        Subject subject = null;
        Klass klass = null;
        for(JsonNode e : elements){
            int id = e.get("id").asInt();

            switch(e.get("type").asInt()){
                case Teacher.TYPE:
                    teacher = teachers.stream().filter(x ->  x.getId() == id).findFirst().orElse(null);
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
                teacher,
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

    public Teacher getTeacher() {
        return teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public Klass getKlass() {
        return klass;
    }
}
