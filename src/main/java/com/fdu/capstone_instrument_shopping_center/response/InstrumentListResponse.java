package com.fdu.capstone_instrument_shopping_center.response;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstrumentListResponse extends Response{

    private List<Instrument> instrumentList;
    private int total;
    public InstrumentListResponse(boolean success, List<Instrument> instrumentList, int total) {
        super(success);
        this.instrumentList = instrumentList;
        this.total = total;
    }


}
