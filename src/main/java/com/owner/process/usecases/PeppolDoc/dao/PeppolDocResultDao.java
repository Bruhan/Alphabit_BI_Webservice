package com.owner.process.usecases.PeppolDoc.dao;

import com.owner.process.persistence.models.PEPPOL_DOC_IDS;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PeppolDocResultDao {
    private List<PEPPOL_DOC_IDS> peppol_doc_ids_all;
}
