package com.api.Controllers;

import com.api.Daos.Connector;
import com.api.Models.*;
import com.api.Serialization.GenderBestSerializer;
import com.api.Serialization.TimeSerialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Path("/players")
public class PlayerController {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response user() throws IOException {
        EntityManager em = Connector.getInstance().startTransaction();
        TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
        List<Player> playersList = query.getResultList();

        ObjectMapper ojm = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new TimeSerialization());
        ojm.registerModule(module);

        String serialized = ojm.writeValueAsString(playersList);
        Connector.getInstance().endTransaction();

        return Response.ok(serialized).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userId(@PathParam("id") Long id) throws JsonProcessingException {
        EntityManager em = Connector.getInstance().startTransaction();
        Query query = em.createQuery("from Player where id = :id");
        query.setParameter("id", id);

        ObjectMapper ojm = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new TimeSerialization(Time.class));
        ojm.registerModule(module);

        String serialized = ojm.writeValueAsString(query.getResultList().get(0));

        Connector.getInstance().endTransaction();

        return Response.ok(serialized).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewUser(Player player) {

        EntityManager em = Connector.getInstance().startTransaction();
        TypedQuery<Gender> query = em.createQuery("SELECT g FROM Gender g WHERE g.name = :name", Gender.class);
        query.setParameter("name", player.getGender().getName());
        Gender g = query.getSingleResult();
        player.setGender(g);
        em.persist(player);
        Connector.getInstance().endTransaction();
        System.out.println(player.getId());
        String response = "{\"id\":" + player.getId() + "}";

        return Response.ok().entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(Player player, @PathParam("id") Long id) {
        EntityManager em = Connector.getInstance().startTransaction();

        Query query = em.createQuery("update Player set age = :age, city = :city, club = :club, country = :country, " +
                "first_name = :first_name, last_name = :last_name, gender = :gender" +
                " where id = :id");
        query.setParameter("age", player.getAge());
        query.setParameter("city", player.getCity());
        query.setParameter("club", player.getClub());
        query.setParameter("country", player.getCountry());
        query.setParameter("first_name", player.getFirst_name());
        query.setParameter("last_name", player.getLast_name());
        query.setParameter("gender", player.getGender());
        query.setParameter("id", id);

        int result = query.executeUpdate();
        String response = "{\"rows_updated\":" + result + "}";
        em.getTransaction().commit();
        return Response.ok().entity(response).build();

    }

    @GET
    @Path("/best/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBestInGender(BestGenderData bestGenderData) throws JsonProcessingException {
        EntityManager em = Connector.getInstance().startTransaction();
        TypedQuery<Player> query = em.createQuery("SELECT distinct p FROM Player p " +
                "JOIN p.gender g JOIN p.times t JOIN t.contest c " +
                "WHERE g.name = :gender AND YEAR(c.date) = :year AND t.time != 0", Player.class)
                .setParameter("gender", bestGenderData.getGender())
                .setParameter("year", bestGenderData.getYear())
                .setMaxResults(bestGenderData.getLimit());

        return getResponse(query);
    }


    @GET
    @Path("/best/{sign}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBestAge(BestAge bestAge, @PathParam("sign") String sign) throws JsonProcessingException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Player> query = null;
        if (sign.equals("under")){
            query = em.createQuery("SELECT distinct p FROM Player p " +
                    "JOIN p.gender g JOIN p.times t JOIN t.contest c " +
                    "WHERE p.age < :age AND YEAR(c.date) = :year AND t.time != 0", Player.class);
        }else if (sign.equals("above")){
            query = em.createQuery("SELECT distinct p FROM Player p " +
                    "JOIN p.gender g JOIN p.times t JOIN t.contest c " +
                    "WHERE p.age > :age AND YEAR(c.date) = :year AND t.time != 0", Player.class);
        }
        em.getTransaction().begin();
        query
                .setParameter("age", bestAge.getAge())
                .setParameter("year", bestAge.getYear())
                .setMaxResults(bestAge.getLimit());

        return getResponse(query);
    }


    private Response getResponse(TypedQuery<Player> query) throws JsonProcessingException {
        List<Player> best = query.getResultList();

        best.sort(Comparator.comparingInt(p -> -p.getTimes().size()));
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(Player.class, new GenderBestSerializer());
        mapper.registerModule(sm);

        String serialized = mapper.writeValueAsString(best);

        Connector.getInstance().endTransaction();

        return Response.ok().entity(serialized).build();
    }
}
