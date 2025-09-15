package com.codeheadsystems.api.rules.engine.v1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * The interface Keys.
 */
@Path("/v1/rules")
public interface Keys {

  /**
   * Read key.
   *
   * @param uuid the uuid
   * @return the key
   */
  @GET
  @Path("/{uuid}")
  @Produces(MediaType.APPLICATION_JSON)
  Key read(@PathParam("uuid") final String uuid);


}
