package com.api.Controllers;

import com.api.Models.AgeCategory;
import com.api.Models.Player;

import javax.persistence.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        GenericEntity<List<Player>> entity = new GenericEntity<List<Player>>(playersList){};
//        GenericEntity<List<AgeCategory>> entity = new GenericEntity<List<AgeCategory>>(playersList){};

        return Response.ok(entity).build();
    }



}
