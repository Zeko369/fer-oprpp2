package hr.fer.oprpp2.console;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        String[] arr = "/foobar".split("/");
        System.out.println(Arrays.toString(arr));
    }
}
