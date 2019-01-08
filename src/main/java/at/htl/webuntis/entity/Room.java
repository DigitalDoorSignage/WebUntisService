package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public class Room {
    public final static int TYPE = 4;
    private int id;
    private String name;
    private String longName;

    public Room(int id, String name, String longName) {
        this.id = id;
        this.name = name;
        this.longName = longName;
    }

    public static Room parse(JsonNode roomJson) {
        return new Room(
                roomJson.get("id").asInt(),
                roomJson.get("name").asText(),
                roomJson.get("longName").asText()
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                Objects.equals(name, room.name) &&
                Objects.equals(longName, room.longName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, longName);
    }
}
