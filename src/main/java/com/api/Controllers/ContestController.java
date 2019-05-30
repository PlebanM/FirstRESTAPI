package com.api.Controllers;

import com.api.Models.Contest;
import com.api.Models.Time;
import com.api.Serialization.ContestsParseSerializer;
import com.api.Serialization.TimeWinnersSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/contests")
public class ContestController {

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContests() throws JsonProcessingException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Contest> query = em.createQuery("SELECT c FROM Contest c", Contest.class);
        List<Contest> contests = query.getResultList();

        ObjectMapper ojm = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(Contest.class, new ContestsParseSerializer());
        sm.addSerializer(Time.class, new TimeWinnersSerializer());
        ojm.registerModule(sm);

        String serialized = ojm.writeValueAsString(contests);
        em.close();
        emf.close();

        return Response.ok().entity(serialized).build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContest(@PathParam("id") long id) throws JsonProcessingException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Contest> query = em.createQuery("SELECT c FROM Contest c WHERE c.id = :id", Contest.class);
        query.setParameter("id", id);
        Contest contest = query.getSingleResult();

        ObjectMapper ojm = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(Contest.class, new ContestsParseSerializer());
        sm.addSerializer(Time.class, new TimeWinnersSerializer());
        ojm.registerModule(sm);

        String serialized = ojm.writeValueAsString(contest);
        em.close();
        emf.close();

        return Response.ok().entity(serialized).build();
    }

}
