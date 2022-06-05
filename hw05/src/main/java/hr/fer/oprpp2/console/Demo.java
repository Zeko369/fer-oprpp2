package hr.fer.oprpp2.console;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Demo {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog.db");
    }
}
