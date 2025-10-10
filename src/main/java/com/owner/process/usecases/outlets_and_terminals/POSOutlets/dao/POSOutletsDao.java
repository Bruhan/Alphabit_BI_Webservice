package com.owner.process.usecases.outlets_and_terminals.POSOutlets.dao;

import com.owner.process.persistence.models.POSOutlets;

import java.util.List;

public interface POSOutletsDao {
    POSOutlets findByOUTLET(String plant, String outlet);

    List<POSOutlets> findAll(String plant);
}
