package at.htl.webuntis.rest;

import at.htl.webuntis.cache.MemCacheItem;
import at.htl.webuntis.cache.RoomTimetableCache;
import at.htl.webuntis.entity.RoomTimetable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("state")
public class StateEndpoint {
    RoomTimetableCache cache = RoomTimetableCache.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getState(@QueryParam("room") String room){
        Optional<MemCacheItem<RoomTimetable>> optItem = cache.get(room);

        if(!optItem.isPresent()){
            return Response.status(404).build();
        }

        RoomTimetable rt = optItem.get().getValue();

        rt.updateCurrentLesson();

        return Response.ok(rt.getCurrentLesson()).build();
    }
}
