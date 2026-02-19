package com.wiiisdom.hr.wp.payloads;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewRegistration {

    @NotNull
    private String constructorName;
    @NotNull
    private String nationality;

    @Size(min = 2, max = 2)
    private List<@Valid DriverRegistration> drivers = new ArrayList<>();
}
