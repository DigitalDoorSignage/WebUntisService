package at.htl.webuntis;


import at.htl.webuntis.entity.*;
import at.htl.webuntis.exception.WebUntisException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class WebUntisClient {
    //region Members
    private static final String ID = UUID.randomUUID().toString();
    private final ObjectMapper objectMapper;
    private final String BASE_URL;
    private final Client client;
    private final WebTarget target;
    private String prefix;
    private String school;
    private String encodedSchool;
    private String username;
    private String password;
    private String sessionId;
    //endregion

    public WebUntisClient(String prefix, String school, String username, String password) {
        this.prefix = prefix;
        this.school = school;
        this.encodedSchool = Base64.getEncoder().encodeToString(school.getBytes());
        this.username = username;
        this.password = password;
        this.BASE_URL = "https://" + prefix + ".webuntis.com/WebUntis/jsonrpc.do";
        objectMapper = new ObjectMapper();
        client = ClientBuilder.newClient();
        target = client.target(BASE_URL);
    }


    public Boolean login(){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("school", school);
        return request(
                "authenticate",
                body -> body.set("params", objectMapper.createObjectNode()
                        .put("user", username)
                        .put("client", "MY CLIENT")
                        .put("password", password)
                ),
                result -> {
                    sessionId = result.get("sessionId").asText();
                    return true; },
                queryParams
        );
    }

    public void logout(){
        request(
                "logout",
                body -> body,
                result -> {},
                null
        );
    }

    public Long getLatestImportTime(){
        return request(
                "getLatestImportTime",
                body -> body,
                result -> { return result.asLong(); },
                null
        );
    }

    public List<Room> getAllRooms(){
        return request(
                "getRooms",
                body -> body,
                result -> {
                    List<Room> rooms = new ArrayList<>();
                    for(JsonNode roomJson : result){
                        if(roomJson.get("name").asText().equals("-"))
                            continue;

                        rooms.add(Room.parse(roomJson));
                    }

                    return rooms;
                },
                null
        );
    }

    public RoomTimetable getTimetableOfRoom(Room room, LocalDate date){
        Response response = client.target("https://" + prefix + ".webuntis.com/WebUntis/api/public/timetable/weekly/data")
                .path("")
                .queryParam("elementType", Room.TYPE)
                .queryParam("elementId", room.getId())
                .queryParam("date", date.format(DateTimeFormatter.ISO_DATE))
                .queryParam("formatId", 1)
                .queryParam("filter.roomGroupId", -1)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID", sessionId)
                .cookie("schoolname", encodedSchool)
                .get();
        try {
            JsonNode body = objectMapper.readTree(response.readEntity(String.class));
            return RoomTimetable.parse(room, body);
        } catch (IOException e) {
            //e.printStackTrace();
        }

        return null;
    }

    //region Util
    private ObjectNode buildRequestBody(String method){
        return objectMapper.createObjectNode()
                .put("id", ID)
                .put("method", method)
                .put("params", "{}")
                .put("jsonrpc", "2.0");
    }

    private Invocation.Builder buildRequest(boolean withCookies, Map<String, String> queryParams){
        WebTarget tempTarget = target;

        if(queryParams != null)
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                tempTarget = tempTarget.queryParam(entry.getKey(), entry.getValue());
            }

        Invocation.Builder request = tempTarget
                    .request(MediaType.APPLICATION_JSON)
                    .header("Content-Type", "application/json");


        if(withCookies){
            request = request
                    .cookie("JSESSIONID", sessionId)
                    .cookie("schoolname", encodedSchool);
        }

        return request;
    }

    private Entity jsonToEntity(JsonNode json){
        return Entity.entity(json, MediaType.TEXT_PLAIN);
    }

    private WebUntisResponse parseResponse(Response response) throws IOException, WebUntisException {
        JsonNode responseBody = objectMapper
                .readTree(response.readEntity(String.class));

        boolean isErrorResponse = responseBody.has("error");

        if(!isErrorResponse){
            return new WebUntisResponse(ID, responseBody.get("result"));
        }
        else{
            JsonNode error = responseBody.get("error");
            throw new WebUntisException(error.get("message").asText(), error.get("code").asInt());
        }
    }

    //region Generic Request
    private void request(String method, Function<ObjectNode, JsonNode> requestBodyBuilder, Consumer<JsonNode> responseHandler, Map<String, String> queryParams){
        JsonNode requestBody = requestBodyBuilder.apply(buildRequestBody(method));
        Response response = buildRequest(method != "authenticate", queryParams).post(jsonToEntity(requestBody));

        try {
            JsonNode result = parseResponse(response).getResult();
            responseHandler.accept(result);
        } catch (IOException e) {
            new WebUntisException("Webuntis crashed", response.getStatus()).printStackTrace();
        } catch (WebUntisException e) {
            e.printStackTrace();
        }
    }

    private <TReturn> TReturn request(String method, Function<ObjectNode, JsonNode> requestBodyBuilder, Function<JsonNode, TReturn> responseHandler, Map<String, String> queryParams){
        JsonNode requestBody = requestBodyBuilder.apply(buildRequestBody(method));
        Response response = buildRequest(method != "authenticate", queryParams).post(jsonToEntity(requestBody));

        try {
            JsonNode result = parseResponse(response).getResult();
            return responseHandler.apply(result);
        } catch (IOException e) {
            new WebUntisException("Webuntis crashed", response.getStatus()).printStackTrace();
        } catch (WebUntisException e) {
            e.printStackTrace();
        }

        return null;
    }
    //endregion
    //endregion

    //region Getter and Setter
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //endregion
}
