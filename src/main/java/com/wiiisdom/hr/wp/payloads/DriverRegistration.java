package com.wiiisdom.hr.wp.payloads;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Valid
@Getter
@Setter
public class DriverRegistration {

    @NotNull
    private String firstName;
    @NonNull
    private String lastName;
    @Min(1)
    private int number;
    @NotNull
    private String nationality;
}
