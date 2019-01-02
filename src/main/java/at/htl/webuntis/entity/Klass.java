package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

public class Klass {
    public final static int TYPE = 1;
    private int id;
    private String name;

    public Klass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Klass parse(JsonNode jsonNode) {
        return new Klass(
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
