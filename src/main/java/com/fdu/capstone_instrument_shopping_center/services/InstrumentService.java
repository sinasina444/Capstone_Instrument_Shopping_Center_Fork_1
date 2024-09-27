package com.fdu.capstone_instrument_shopping_center.services;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;

import java.util.List;

public interface InstrumentService {
    List<Instrument> getAllInstruments();

    Instrument addNewInstrument(Instrument instrument);

    Instrument addNewInstrument(String name, String brand, String category);
}
