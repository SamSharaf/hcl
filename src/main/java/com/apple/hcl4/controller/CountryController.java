package com.apple.hcl4.controller;

import com.apple.hcl4.domain.Country;
import com.apple.hcl4.repository.CountryRepository;
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
 * REST controller for managing Country.
 */
@Path("/api/country")
@RolesAllowed(USER)
public class CountryController {

    @Inject
    private Logger log;

    @Inject
    private CountryRepository countryRepository;

    private static final String ENTITY_NAME = "country";

    /**
     * POST : Create a new country.
     *
     * @param country the country to create
     * @return the Response with status 201 (Created) and with body the new
     * country, or with status 400 (Bad Request) if the country has already an
     * ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "create a new country", description = "Create a new country")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCountry(Country country) throws URISyntaxException {
        log.debug("REST request to save Country : {}", country);
        countryRepository.create(country);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/country/" + country.getId())),
                ENTITY_NAME, country.getId().toString())
                .entity(country).build();
    }

    /**
     * PUT : Updates an existing country.
     *
     * @param country the country to update
     * @return the Response with status 200 (OK) and with body the updated
     * country, or with status 400 (Bad Request) if the country is not valid, or
     * with status 500 (Internal Server Error) if the country couldn't be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Timed
    @Operation(summary = "update country", description = "Updates an existing country")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCountry(Country country) throws URISyntaxException {
        log.debug("REST request to update Country : {}", country);
        countryRepository.edit(country);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, country.getId().toString())
                .entity(country).build();
    }

    /**
     * GET : get all the countries.
     *
     * @param page the pagination information
     * @param size the pagination size information
     *
     * @return the Response with status 200 (OK) and the list of countries in
     * body
     * @throws URISyntaxException if there is an error to generate the
     * pagination HTTP headers
     */
    @Timed
    @Operation(summary = "get all the countries")
    @APIResponse(responseCode = "200", description = "OK")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public Response getAllCountries(@QueryParam("page") int page, @QueryParam("size") int size) throws URISyntaxException {
        log.debug("REST request to get all Countries");
        List<Country> countries = countryRepository.findRange(page * size, size);
        ResponseBuilder builder = Response.ok(countries);
        PaginationUtil.generatePaginationHttpHeaders(builder, new Page(page, size, countryRepository.count()), "/resources/api/country");
        return builder.build();
    }

    /**
     * GET /:id : get the "id" country.
     *
     * @param id the id of the country to retrieve
     * @return the Response with status 200 (OK) and with body the country, or
     * with status 404 (Not Found)
     */
    @Timed
    @Operation(summary = "get the country")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountry(@PathParam("id") Long id) {
        log.debug("REST request to get Country : {}", id);
        Country country = countryRepository.find(id);
        return Optional.ofNullable(country)
                .map(result -> Response.status(Response.Status.OK).entity(country).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:id : remove the "id" country.
     *
     * @param id the id of the country to delete
     * @return the Response with status 200 (OK)
     */
    @Timed
    @Operation(summary = "remove the country")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Not Found")
    @DELETE
    @Path("/{id}")
    public Response removeCountry(@PathParam("id") Long id) {
        log.debug("REST request to delete Country : {}", id);
        countryRepository.remove(countryRepository.find(id));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, id.toString()).build();
    }

}
