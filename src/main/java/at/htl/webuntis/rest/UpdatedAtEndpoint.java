package at.htl.webuntis.rest;

import at.htl.webuntis.cache.MemCacheItem;
import at.htl.webuntis.cache.RoomTimetableCache;
import at.htl.webuntis.entity.RoomTimetable;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("updatedAt")
@Stateless
public class UpdatedAtEndpoint {
    RoomTimetableCache cache = RoomTimetableCache.getInstance();

    @GET
    public Response getUpdatedAt(@QueryParam("room") String room){
        Optional<MemCacheItem<RoomTimetable>> optItem = cache.get(room);

        if(!optItem.isPresent()){
            return Response.status(404).build();
        }

        return Response.ok(optItem.get().getUpdatedAt()).build();
    }
}
