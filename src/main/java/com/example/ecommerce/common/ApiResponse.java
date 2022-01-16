package com.example.ecommerce.common;

import java.time.LocalDate;

public class ApiResponse {
    public final boolean succes;
    public final String message;

    public ApiResponse(boolean succes, String message) {
        this.succes = succes;
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public boolean isSucces(){
        return this.succes;
    }

    public String getTimestamp(){
        return LocalDate.now().toString();
    }
}
