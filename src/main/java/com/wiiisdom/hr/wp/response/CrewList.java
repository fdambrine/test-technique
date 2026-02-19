package com.wiiisdom.hr.wp.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wiiisdom.hr.wp.database.model.Driver;

import lombok.Getter;

@Getter
public class CrewList {

    private final Map<String, List<Driver>> crews = new HashMap<>();

}
