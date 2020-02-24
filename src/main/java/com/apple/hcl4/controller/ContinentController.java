package com.apple.hcl4.controller;

import com.apple.hcl4.domain.Continent;
import com.apple.hcl4.repository.ContinentRepository;
import com.apple.hcl4.controller.util.HeaderUtil;
import static com.apple.hcl4.security.AuthoritiesConstants.USER;
import org.slf4j.Logger;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.apple.hcl4.controller.util.Page;
import com.apple.hcl4.controller.util.PaginationUtil;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 * REST controller for managing Continent.
 */
@Path("/api/continent")
@RolesAllowed(USER)
public class ContinentController {

    @Inject
    private Logger log;

    @Inject
    private ContinentRepository continentRepository;

    private static final String ENTITY_NAME = "continent";

    /**
     * POST : Create a new continent.
     *
     * @param continent the continent to create
     * @return the Response with status 201 (Created) and with body the new
     * continent, or with status 400 (Bad Request) if the continent has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new continent", description = "Create a new continent")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContinent(Continent continent) throws URISyntaxException {
        log.debug("REST request to save Continent : {}", continent);
        continentRepository.create(continent);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/continent/" + continent.getId())),
                ENTITY_NAME, continent.getId().toString())
                .entity(continent).build();
    }

    /**
     * PUT : Updates an existing continent.
     *
     * @param continent the continent to update
     * @return the Response with status 200 (OK) and with body the updated
     * continent, or with status 400 (Bad Request) if the continent is not
     * valid, or with status 500 (Internal Server Error) if the continent
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update continent", description = "Updates an existing continent")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateContinent(Continent continent) throws URISyntaxException {
        log.debug("REST request to update Continent : {}", continent);
        continentRepository.edit(continent);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, continent.getId().toString())
                .entity(continent).build();
    }

    /**
     * GET : get all the continents.
     *
     * @param page the pagination information
     * @param size the pagination size information
     *
     * @return the Response with status 200 (OK) and the list of continents in
     * body
     * @throws URISyntaxException if there is an error to generate the
     * pagination HTTP headers
     */
    @Timed
    @Operation(summary = "get all the continents")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public Response getAllContinents(@QueryParam("page") int page, @QueryParam("size") int size) throws URISyntaxException {
        log.debug("REST request to get all Continents");
        List<Continent> continents = continentRepository.findRange(page * size, size);
        ResponseBuilder builder = Response.ok(continents);
        PaginationUtil.generatePaginationHttpHeaders(builder, new Page(page, size, continentRepository.count()), "/resources/api/continent");
        return builder.build();
    }

    /**
     * GET /:id : get the "id" continent.
     *
     * @param id the id of the continent to retrieve
     * @return the Response with status 200 (OK) and with body the continent, or
     * with status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the continent")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContinent(@PathParam("id") Long id) {
        log.debug("REST request to get Continent : {}", id);
        Continent continent = continentRepository.find(id);
        return Optional.ofNullable(continent)
                .map(result -> Response.status(Response.Status.OK).entity(continent).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" continent.
     *
     * @param id the id of the continent to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the continent")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeContinent(@PathParam("id") Long id) {
        log.debug("REST request to delete Continent : {}", id);
        continentRepository.remove(continentRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }

}
