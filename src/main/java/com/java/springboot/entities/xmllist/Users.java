package com.java.springboot.entities.xmllist;


import com.java.springboot.entities.User;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Users {

    @XmlElement(name = "user")
    List<User> users;

}
