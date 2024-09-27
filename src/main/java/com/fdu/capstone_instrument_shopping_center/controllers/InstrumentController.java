package com.fdu.capstone_instrument_shopping_center.controllers;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.repositories.InstrumentRepository;
import com.fdu.capstone_instrument_shopping_center.response.InstrumentResponse;
import com.fdu.capstone_instrument_shopping_center.response.Response;
import com.fdu.capstone_instrument_shopping_center.services.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Instrument")
public class InstrumentController {

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    InstrumentService instrumentService;

    @GetMapping("/getInstrumentList")
    public List<Instrument> getInstrumentList() {
        return instrumentService.getAllInstruments();
    }

    @PostMapping("/postInstrument")
    public Response addInstrument(@RequestBody Instrument instrument) {
        instrumentService.addNewInstrument(instrument);
        return new InstrumentResponse(true, instrument);
    }
}
