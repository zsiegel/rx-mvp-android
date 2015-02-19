package com.zsiegel.core.model;

/**
 * @author zsiegel
 */
public class User {

    public long id;
    public String firstName;
    public String lastName;
    public int age;

    @Override
    public String toString() {
        return "ID : " + id + " " + firstName + " " + lastName + " : age " + age;
    }
}
