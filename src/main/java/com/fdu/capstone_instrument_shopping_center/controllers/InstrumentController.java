package com.fdu.capstone_instrument_shopping_center.controllers;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.repositories.InstrumentRepository;
import com.fdu.capstone_instrument_shopping_center.response.InstrumentListResponse;
import com.fdu.capstone_instrument_shopping_center.response.InstrumentResponse;
import com.fdu.capstone_instrument_shopping_center.response.Response;
import com.fdu.capstone_instrument_shopping_center.services.InstrumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Instrument")
@Slf4j
public class InstrumentController {

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    InstrumentService instrumentService;

    @GetMapping("/getInstrumentList")
    public InstrumentListResponse getInstrumentListByPage(
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page
    ) {
        return instrumentService.getInstrumentResponseByPage(pageSize, page);
    }

    @GetMapping("/getAllInstrumentList")
    public List<Instrument> getAllInstrumentList() {
        return instrumentService.getAllInstruments();
    }

    @PostMapping("/postInstrument")
    public Response addInstrument(@RequestBody Instrument instrument) {
        instrumentService.addNewInstrument(instrument);
        return new InstrumentResponse(true, instrument);
    }


}
