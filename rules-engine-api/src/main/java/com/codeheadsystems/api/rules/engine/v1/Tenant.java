package com.codeheadsystems.api.rules.engine.v1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The interface Keys.
 */
@Path("/v1/tenant")
public interface Tenant {

  @GET
  @Path("/{tenant}")
  @Produces(MediaType.APPLICATION_JSON)
  Response read(@PathParam("tenant") final String tenant);


}
