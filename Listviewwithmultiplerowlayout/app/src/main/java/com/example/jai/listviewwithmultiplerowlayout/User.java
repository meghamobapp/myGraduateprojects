package com.example.jai.listviewwithmultiplerowlayout;

/**
 * Created by Jai on 10/1/2016.
 */
import java.util.ArrayList;

public class User {
    public String name;
    public String hometown;

    public User(String name, String hometown) {
        this.name = name;
        this.hometown = hometown;
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User("Harry", "San Diego"));
        users.add(new User("Marla", "San Francisco"));
        users.add(new User("Sarah", "San Marco"));
        return users;
    }
}