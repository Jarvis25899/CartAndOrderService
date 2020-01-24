package com.example.CartAndOrderService.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class APIResponse<T> {

    private int status;
    private String message;
    private T data;

    public APIResponse(){}

    public APIResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public APIResponse(int status, String message, T data) {
        this.status=status;
        this.message=message;
        this.data = data;
    }
}
