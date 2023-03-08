package com.wiiisdom.hr.wp.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "drivers")
@Getter
@Setter
public class Driver {

    @Id
    @Column(name = "driverId")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int number;

    private String forename;
    private String surname;
    private String nationality;

}
