package com.java.springboot.util;

import com.java.springboot.entities.User;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class MockUtil {

    public static User getUser(String name, String userName, String city, String desc, LocalDate doj, ZonedDateTime exactDob, int status){
        return User.builder().name(name)
                .userName(userName)
                .city(city)
                .description(desc)
                .dateOfJoining(doj)
                .exactDob(exactDob)
                .status(status)
                .build();
    }

    public static List<User> getUsers(){
        return Arrays.asList(
                getUser("Jacob", "jacob", "Mumbai",
                        "jacob likes to draw", LocalDate.now().minusYears(25)
                        ,ZonedDateTime.now().minusYears(40), 1),
                getUser("Jamie", "jamie", "Bangalore",
                        "jamie likes to write", LocalDate.now().minusYears(25)
                        ,ZonedDateTime.now().minusYears(41), 1),
                getUser("Jenny", "jenny", "Chennai",
                        "jenny likes to paint", LocalDate.now().minusYears(25)
                        ,ZonedDateTime.now().minusYears(42), 1),
                getUser("Xing", "xing", "Hokkaido",
                        "シンさんは北海道出身です", LocalDate.now().minusYears(25)
                        ,ZonedDateTime.now().minusYears(43), 1)
        );
    }

}
