package com.facility.management.usecases.dashboard.dto;

import com.facility.management.usecases.dashboard.enums.OWCMachineStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeOWCMachineStatus {
    private OWCMachineStatus owcMachineStatus;
    private String machineId;
}
