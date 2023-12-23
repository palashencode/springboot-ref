package com.java.springboot.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.springboot.util.JAXBLocalDateAdapter;
import com.java.springboot.util.JAXBZonedDateTimeAdapter;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "userName", "city", "description", "dateOfJoining", "exactDob", "status"})
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    @XmlAttribute
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "USERNAME")
    private String userName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String description;

    @CsvDate(value = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @XmlJavaTypeAdapter(JAXBLocalDateAdapter.class)
    @Column(nullable = false, name = "DATEOFJOINING")
    private LocalDate dateOfJoining;

    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @XmlJavaTypeAdapter(JAXBZonedDateTimeAdapter.class)
    @Column(nullable = false, name = "EXACTDOB")
    private ZonedDateTime exactDob;

    @Column(nullable = false)
    private Integer status;

}
