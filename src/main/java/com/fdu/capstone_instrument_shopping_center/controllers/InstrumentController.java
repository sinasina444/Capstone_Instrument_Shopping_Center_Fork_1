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
import java.util.Optional;

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

    @GetMapping("/getInstrumentBySku")
    public Response getInstrumentBySku(@RequestBody String sku) {
        Optional<Instrument> optionalInstrument = instrumentRepository.findBySku(sku);
        if(optionalInstrument.isEmpty()) {
            log.info("Cannot find instrument by sku. Please check if sku is valid.");
            return new Response(false);
        }
        return new InstrumentResponse(true, optionalInstrument.get());
    }


}
