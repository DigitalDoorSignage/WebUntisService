package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

public class Subject {
    public final static int TYPE = 3;
    private int id;
    private String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Subject parse(JsonNode jsonNode) {
        return new Subject(
                jsonNode.get("id").asInt(),
                jsonNode.get("name").asText()
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
