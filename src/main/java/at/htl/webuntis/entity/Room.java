package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

public class Room {
    public final static int TYPE = 4;
    private int id;
    private String name;
    private String longName;
    private boolean active;
    private String building;

    public Room(int id, String name, String longName, boolean active, String building) {
        this.id = id;
        this.name = name;
        this.longName = longName;
        this.active = active;
        this.building = building;
    }

    public static Room parse(JsonNode roomJson) {
        return new Room(
                roomJson.get("id").asInt(),
                roomJson.get("name").asText(),
                roomJson.get("longName").asText(),
                roomJson.get("active").asBoolean(),
                roomJson.get("building").asText()
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLongName() {
        return longName;
    }

    public boolean isActive() {
        return active;
    }

    public String getBuilding() {
        return building;
    }
}
