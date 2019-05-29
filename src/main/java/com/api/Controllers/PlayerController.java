package com.api.Controllers;

import com.api.Models.AgeCategory;
import com.api.Models.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
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

        String serialized = ojm.writeValueAsString(playersList);
        em.close();
        emf.close();
        GenericEntity<List<Player>> entity = new GenericEntity<List<Player>>(playersList){};

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





}
