package com.java.springboot.entities;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "LABELS")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "K")
    private String key;

    @Column(name = "V")
    private String value;

    @Column(name = "LANG")
    private String languageCode;
}
