package com.api.Controllers;

import com.api.Models.Gender;
import com.api.Models.Player;
import com.api.Models.Time;
import com.api.Serialization.TimeSerialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/players")
public class PlayerController {

    @GET
    @Path("/get/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response user() throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
        List<Player> playersList = query.getResultList();

        ObjectMapper ojm = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new TimeSerialization());
        ojm.registerModule(module);

        String serialized = ojm.writeValueAsString(playersList);
        em.close();
        emf.close();

        return Response.ok(serialized).build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userId(@PathParam("id") int id) throws JsonProcessingException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("from Player where id = :id");
        query.setParameter("id", Long.valueOf(id));

        ObjectMapper ojm = new ObjectMapper();
        String serialized = ojm.writeValueAsString(query.getResultList().get(0));

        return Response.ok(serialized).build();
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewUser(Player player) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Gender> query = em.createQuery("SELECT g FROM Gender g WHERE g.name = :name", Gender.class);
        query.setParameter("name", player.getGender().getName());
        Gender g = query.getSingleResult();
        player.setGender(g);
        em.persist(player);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println(player.getId());
        String response = "{\"id\":" + player.getId() + "}";

        return Response.ok().entity(response).build();
    }



}
