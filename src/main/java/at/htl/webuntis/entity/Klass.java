package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public class Klass {
    public final static int TYPE = 1;
    private int id;
    private String name;

    public Klass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Klass(){

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Klass klass = (Klass) o;
        return id == klass.id &&
                Objects.equals(name, klass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
