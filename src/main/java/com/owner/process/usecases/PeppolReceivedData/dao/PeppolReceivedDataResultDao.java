package com.owner.process.usecases.PeppolReceivedData.dao;

import com.owner.process.persistence.models.PEPPOL_RECEIVED_DATA;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PeppolReceivedDataResultDao {
    private List<PEPPOL_RECEIVED_DATA> peppol_received_data;
}
