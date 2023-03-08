package com.wiiisdom.hr.wp.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wiiisdom.hr.wp.database.model.Constructor;
import com.wiiisdom.hr.wp.database.model.Crew;
import com.wiiisdom.hr.wp.database.model.Driver;
import com.wiiisdom.hr.wp.database.model.Season;
import com.wiiisdom.hr.wp.payloads.CrewRegistration;
import com.wiiisdom.hr.wp.payloads.DriverRegistration;
import com.wiiisdom.hr.wp.response.CreationResponse;
import com.wiiisdom.hr.wp.response.CrewList;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/crew")
public class CrewEndpoint {

    private static final Logger LOGGER = Logger.getLogger(CrewEndpoint.class.getCanonicalName());
    @Inject
    private EntityManager entityManager;

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{season}/crews")
    @Operation(description = "Gets all the crews of a given season")
    public CrewList crewOfYear(@PathParam("season") String season) {
        Season seasonEntity = entityManager.createQuery(
                String.format("select e from season e where e.name='%s'", season),
                Season.class).getSingleResult();

        CrewList list = new CrewList();
        for (Crew crew : seasonEntity.getRegisteredCrew()) {
            if (!list.getCrews().containsKey(crew.getConstructor().getName())) {
                // logically it should be 2 drivers per crew so use this one
                list.getCrews().put(crew.getConstructor().getName(), new ArrayList<>(2));
            }
            list.getCrews().get(crew.getConstructor().getName()).add(crew.getDriver());
        }
        return list;
    }

    @GET
    @Path("/{constructor}/crew")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Gets the crew of a given constructor for the current season")
    public CrewList crewOfConstructor(@PathParam("constructor") String season) {
        TypedQuery<Crew> query = entityManager.createQuery("select e from crew e where e.constructor.name = "
                + ":name order by e.season.name desc ", Crew.class);
        query.setParameter("name", season);
        List<Crew> crews = query.getResultList();
        if (crews.isEmpty()) {
            throw new NotFoundException();
        }
        CrewList result = new CrewList();
        result.getCrews().put(season, new ArrayList<>(2));
        String currentSeason = crews.get(0).getSeason().getName();
        for (Crew crew : crews) {
            if (!currentSeason.equals(crew.getSeason().getName())) {
                break;
            }
            result.getCrews().get(season).add(crew.getDriver());
        }
        return result;
    }

    @POST
    @RolesAllowed("administrator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{season}/crew")
    public CreationResponse registerCrew(@Context SecurityContext securityContext, @PathParam("season") String season,
            @Valid CrewRegistration registration) {
        LOGGER.log(Level.INFO, securityContext.getUserPrincipal().getName() + " will create season");
        Map<String, Object> condition = Collections.singletonMap("name", season);
        Season seasonEntity = entityManager.find(Season.class, condition);
        if (seasonEntity == null) {
            throw new NotFoundException();
        }
        Constructor constructor = new Constructor();
        constructor.setName(registration.getConstructorName());
        constructor.setNationality(registration.getNationality());
        entityManager.merge(constructor);
        for (DriverRegistration driverRegistration : registration.getDrivers()) {
            Driver driver = new Driver();
            driver.setForename(driverRegistration.getFirstName());
            driver.setSurname(driverRegistration.getLastName());
            driver.setNumber(driverRegistration.getNumber());
            driver.setNationality(driver.getNationality());
            entityManager.merge(driver);
            Crew crew = new Crew();
            crew.setConstructor(constructor);
            crew.setDriver(driver);
            crew.setSeason(seasonEntity);
            entityManager.merge(crew);

        }
        entityManager.flush();
        return new CreationResponse(constructor.getId());
    }
}
