package com.codeheadsystems.api.rules.engine.v1;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * The interface Event.
 */
@Path("/v1/event/tenant/{tenant}/eventType/{eventType}")
public interface Event {

  /**
   * Read execution result.
   *
   * @param tenant        the tenant
   * @param eventType     the event type
   * @param eventId       the event id
   * @param jsonEventData the json event data
   * @return the execution result
   */
  @PUT
  @Path("/{eventId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ExecutionResult execute(@PathParam("tenant") final String tenant,
                          @PathParam("eventType") final String eventType,
                          @PathParam("eventId") final String eventId,
                          final String jsonEventData);


}
