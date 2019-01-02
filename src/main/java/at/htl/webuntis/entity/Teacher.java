package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

public class Teacher {
    public final static int TYPE = 2;
    private int id;
    private String shortName;

    public Teacher(int id, String shortName) {
        this.id = id;
        this.shortName = shortName;
    }

    public static Teacher parse(JsonNode jsonNode) {
        return new Teacher(
                jsonNode.get("id").asInt(),
                jsonNode.get("name").asText()
        );
    }

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }
}
