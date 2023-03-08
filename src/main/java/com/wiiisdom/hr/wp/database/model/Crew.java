package com.wiiisdom.hr.wp.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "crew")
@Getter
@Setter
public class Crew{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "constructorId")
    private Constructor constructor;
    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;
    @ManyToOne
    @JoinColumn(name = "seasonId")

    private Season season;
}
