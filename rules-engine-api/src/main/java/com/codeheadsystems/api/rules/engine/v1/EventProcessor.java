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
@Path("/v1/event/tenant/{tenant}/event/{event}/version/{version}")
public interface EventProcessor {

  /**
   * Executes the rule engine for the given event.
   * @param tenant
   * @param event
   * @param version
   * @param eventId
   * @param jsonEventData
   * @return
   */
  @PUT
  @Path("/{eventId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ExecutionResult execute(@PathParam("tenant") final String tenant,
                          @PathParam("event") final String event,
                          @PathParam("version") final String version,
                          @PathParam("eventId") final String eventId,
                          final String jsonEventData);


}
