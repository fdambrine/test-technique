package com.wiiisdom.hr.wp.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "constructors")
@Getter
@Setter
public class Constructor {

    @Id
    @Column(name = "constructorId")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;
    private String nationality;

}
