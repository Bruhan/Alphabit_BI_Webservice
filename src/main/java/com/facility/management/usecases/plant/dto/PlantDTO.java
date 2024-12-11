package com.facility.management.usecases.plant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantDTO {
    private String plant;
    private Byte isAutoSupervisorLogin;
    private Byte isAutoSupervisorLogout;
}
