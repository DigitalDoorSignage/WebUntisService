package at.htl.webuntis.entity;

import com.fasterxml.jackson.databind.JsonNode;

public class WebUntisResponse {
    private String id;
    private JsonNode result;

    public WebUntisResponse(String id, JsonNode result) {
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public JsonNode getResult() {
        return result;
    }
}
