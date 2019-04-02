package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public class Teacher {
    public final static int TYPE = 2;
    private int id;
    private String shortName;

    public Teacher(int id, String shortName) {
        this.id = id;
        this.shortName = shortName;
    }

    public Teacher() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id &&
                Objects.equals(shortName, teacher.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName);
    }
}
