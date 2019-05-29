package com.api.Controllers;

import com.api.Models.Gender;
import com.api.Models.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/players")
public class PlayerController {

    @GET
    @Path("/get/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response user() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
//        TypedQuery<AgeCategory> query = em.createNamedQuery("AgeCategory.findAll", AgeCategory.class);
        List<Player> playersList = query.getResultList();
//        List<AgeCategory> playersList = query.getResultList();
//        System.out.println(playersList.get(0).getClub());
        em.close();
        emf.close();
        GenericEntity<List<Player>> entity = new GenericEntity<List<Player>>(playersList) {
        };
//        GenericEntity<List<AgeCategory>> entity = new GenericEntity<List<AgeCategory>>(playersList){};

        return Response.ok(entity).build();
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewUser(Player player) {
        System.out.println(player.getCity());
        System.out.println(player.getAge());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Gender> query = em.createQuery("SELECT g FROM Gender g WHERE id = 1", Gender.class);
        Gender g = query.getSingleResult();
        player.setGender(g);
        em.merge(player);
        em.getTransaction().commit();
        em.close();
        emf.close();

        return Response.ok().entity("ok").build();
    }

}
