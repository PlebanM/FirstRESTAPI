package com.api.Daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Connector {

    private static Connector connector = new Connector();

    private EntityManagerFactory emf;
    private EntityManager em;

    public static Connector getInstance() {
        return connector;
    }

    private Connector() {
        emf = Persistence.createEntityManagerFactory("sts-timing");
    }

    public EntityManager startTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        return em;
    }

    public void endTransaction() {
        if (em != null && em.isOpen()) {
            em.getTransaction().commit();
            em.close();
        }
    }


}
