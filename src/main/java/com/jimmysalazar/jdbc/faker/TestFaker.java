package com.jimmysalazar.jdbc.faker;

import com.github.javafaker.Faker;

public class TestFaker {
    public static void main(String[] args) {
        Faker faker = new Faker();
        System.out.println(faker.name().firstName());
        System.out.println(faker.name().lastName());
        System.out.println(faker.dragonBall().character());
    }
}
