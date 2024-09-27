package com.fdu.capstone_instrument_shopping_center.response;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstrumentResponse extends Response{

    private Instrument instrument;
    public InstrumentResponse(boolean success, Instrument instrument) {
        super(success);
        this.instrument = instrument;
    }
}
