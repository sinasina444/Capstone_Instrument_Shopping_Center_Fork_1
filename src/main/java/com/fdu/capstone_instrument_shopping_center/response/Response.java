package com.fdu.capstone_instrument_shopping_center.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    protected boolean success;
    protected int code;
    protected String message;

    public Response() {
        super();
    }

    public Response(boolean success) {
        super();
        this.success = success;
        this.code = success ? 200 : 400;
        this.message = "";
    }

    public Response(boolean success, String message) {
        super();
        this.success = success;
        this.code = success ? 200 : 400;
        this.message = message;
    }

    public Response(boolean success, int code, String message) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
