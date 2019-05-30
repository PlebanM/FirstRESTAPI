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
        Player p1 = new Player("Marcin", "Jakis", "Poland", "Wichury z lisiej dziury", "Krakow", g1, 26);
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


        Gender g2 = new Gender("woman");
        Player p2 = new Player("Kamil", "Nazwisko", "USA", "Run for Fun", "Rzeszów", g2, 48);
        Contest c2 = new Contest(new Date(), 2000, "Mexico", 100, "www.nowastrona.pl");
        Time t2 = new Time(p2, c2, 600);
        AgeCategory category2 = new AgeCategory("O60", 16, 20);
        EntityTransaction transaction2 = em.getTransaction();
        transaction2.begin();
        em.persist(g2);
        em.persist(p2);
        em.persist(c2);
        em.persist(t2);
        em.persist(category);
        transaction2.commit();

        EntityTransaction transaction3 = em.getTransaction();
        transaction3.begin();
        Contest c3 = new Contest(new Date(), 1000, "Rzym", 20, "www.nowasssstrona.pl");
        Time t3 = new Time(p2, c3, 123);
        em.persist(c3);
        em.persist(t3);
        transaction3.commit();

    }
}
