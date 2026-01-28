package org.example.retoconjuntoad_di_2.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataProvider {

    private static final String DB_FILE = "data/data.odb";

    private static EntityManagerFactory entityManagerFactory = null;

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(DB_FILE);
        }
        return entityManagerFactory;
    }
}
