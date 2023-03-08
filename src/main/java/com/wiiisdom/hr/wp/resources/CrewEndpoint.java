package com.wiiisdom.hr.wp.resources;

import com.wiiisdom.hr.wp.database.model.Crew;
import com.wiiisdom.hr.wp.response.CreationResponse;
import com.wiiisdom.hr.wp.response.CrewList;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// The Java class will be hosted at the URI path "/myresource"
@Path("/crew")
public class CrewEndpoint {

    @Inject
    private EntityManager entityManager;

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces(MediaType.APPLICATION_JSON)
    public CrewList crewOfYear(String season) {

        return new CrewList();
    }


    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces(MediaType.APPLICATION_JSON)
    public CrewList crewOfConstructor(String season) {

        return new CrewList();
    }



    @POST
    public CreationResponse registerCrew(String season) {
        throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
    }
}
