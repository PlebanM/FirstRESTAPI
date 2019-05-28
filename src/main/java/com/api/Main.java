package com.api;

import com.api.Models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sts-timing");
        EntityManager em = emf.createEntityManager();

        populateDB(em);
        em.clear();

        em.close();
        emf.close();
    }

    private static void populateDB(EntityManager em){
        Gender g1 = new Gender("male");
        Player p1 = new Player("Poland", "Wichury z lisiej dziury", "Krakow", g1, 26);
        Contest c1 = new Contest(new Date(), 4000, "Warsau", 200, "www.localhost.pl");
        Time t1 = new Time(p1, c1, 3600);
        AgeCategory category = new AgeCategory("O20", 16, 20);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(g1);
        em.persist(p1);
        em.persist(c1);
        em.persist(t1);
        em.persist(category);
        transaction.commit();
    }
}
