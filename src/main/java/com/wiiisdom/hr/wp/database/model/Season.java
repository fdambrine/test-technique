package com.wiiisdom.hr.wp.database.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "season")
@Getter
@Setter
public class Season {

    @Id
    @Column(name = "seasonId")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany(mappedBy = "season")
    private List<Crew> registeredCrew = new ArrayList<>();

}
