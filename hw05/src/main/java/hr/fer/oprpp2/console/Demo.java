package hr.fer.oprpp2.console;

import hr.fer.oprpp2.model.BlogUser;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog.db");
//        BlogUser user = new BlogUser();
//        user.getId();
        String[] arr = "/foobar".split("/");
        System.out.println(Arrays.toString(arr));
    }
}
