package com.api.Controllers;

import com.api.Daos.Connector;
import com.api.Models.Contest;
import com.api.Models.Player;
import com.api.Models.Time;
import com.api.Models.TimeObject;
import com.api.Serialization.AllFieldsFromTimeSerializer;
import com.api.Serialization.TimeSerialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/time")
public class TimeController {


    @GET
    @Path("/get/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response user() throws IOException {
        EntityManager em = Connector.getInstance().startTransaction();
        TypedQuery<Time> query = em.createNamedQuery("Time.findAll", Time.class);
        List<Time> timeList = query.getResultList();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new AllFieldsFromTimeSerializer(Time.class));
        mapper.registerModule(module);

        String serialized = mapper.writeValueAsString(timeList);

        Connector.getInstance().endTransaction();

        return Response.ok(serialized).build();
    }

    @GET
    @Path("/get/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userId(Player player, @PathParam("id") Long id) throws JsonProcessingException {
        EntityManager em = Connector.getInstance().startTransaction();
        Query query = em.createQuery("from Time where player.id = :id");
        query.setParameter("id", id);

        ObjectMapper ojm = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new TimeSerialization());
        ojm.registerModule(module);

        String serialized = ojm.writeValueAsString(query.getResultList());

        Connector.getInstance().endTransaction();

        return Response.ok(serialized).build();
    }

    @GET
    @Path("/get/contest/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userId(Contest contest, @PathParam("id") Long id) throws JsonProcessingException {
        EntityManager em = Connector.getInstance().startTransaction();
        Query query = em.createQuery("from Time where contest.id = :id");
        query.setParameter("id", id);

        ObjectMapper ojm = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new AllFieldsFromTimeSerializer(Time.class));
        ojm.registerModule(module);

        String serialized = ojm.writeValueAsString(query.getResultList());

        Connector.getInstance().endTransaction();

        return Response.ok(serialized).build();
    }


    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserToContest(TimeObject time) {
        EntityManager em = Connector.getInstance().startTransaction();
        em.getTransaction().begin();

        int isAdd = em.createNativeQuery("INSERT INTO time (time, contest_id, player_id) VALUES (?,?,?)")
                .setParameter(1, time.getTime())
                .setParameter(2, time.getIdContest())
                .setParameter(3, time.getIdPlayer()).executeUpdate();

        Connector.getInstance().endTransaction();

        String response = "{\"addTime\":" + isAdd + "}";

        return Response.ok().entity(response).build();
    }
}
