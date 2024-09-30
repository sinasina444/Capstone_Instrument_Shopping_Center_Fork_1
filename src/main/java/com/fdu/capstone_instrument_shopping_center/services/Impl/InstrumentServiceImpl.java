package com.fdu.capstone_instrument_shopping_center.services.Impl;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.repositories.InstrumentRepository;
import com.fdu.capstone_instrument_shopping_center.response.InstrumentListResponse;
import com.fdu.capstone_instrument_shopping_center.services.InstrumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InstrumentServiceImpl implements InstrumentService {
    static final String UNKNOWN = "unknown";

    private final InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

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

    @Override
    public InstrumentListResponse getInstrumentResponseByPage(int pageSize, int page) {
        if(pageSize <= 0 || page <= 0) {
            return new InstrumentListResponse(false, new ArrayList<>(), 0);
        }
        // Return Instrument List based on pageSize and current page number.
        List<Instrument> allInstrument = getAllInstruments();
        int total = allInstrument.size();
        // Calculate the instrument index , including starting index and ending index
        int startIndex = (page - 1) * pageSize;
        if(startIndex >= total) {
            // Current page has no product , because it is larger than total instruments
            log.warn("Current page: {} has no instruments to show.", page);
            return new InstrumentListResponse(true, new ArrayList<>(), total);
        }
        int endIndex = Math.min(total, startIndex + pageSize);
        List<Instrument> resInstrumentList = allInstrument.subList(startIndex, endIndex);
        return new InstrumentListResponse(true, resInstrumentList, total);
    }

}
