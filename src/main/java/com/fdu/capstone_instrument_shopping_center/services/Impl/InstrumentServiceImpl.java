package com.fdu.capstone_instrument_shopping_center.services.Impl;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.repositories.InstrumentRepository;
import com.fdu.capstone_instrument_shopping_center.services.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentServiceImpl implements InstrumentService {
    static final String UNKNOWN = "unknown";

    @Autowired
    InstrumentRepository instrumentRepository;


    @Override
    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    @Override
    public Instrument addNewInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    @Override
    public Instrument addNewInstrument(String name, String brand, String category) {
        Instrument instrument = new Instrument(name, brand, category);
        instrument.setCategory(UNKNOWN);
        instrument.setDescription("Blank description");
        instrument.setMaterial(UNKNOWN);
        instrument.setPrice(0.0);
        instrument.setStockQuantity(0);
        instrument.setRating(0.0);
        instrument.setImageURL(UNKNOWN);
        return instrument;
    }
}
