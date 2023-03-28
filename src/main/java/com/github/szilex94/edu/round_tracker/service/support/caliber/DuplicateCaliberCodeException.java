package com.github.szilex94.edu.round_tracker.service.support.caliber;

public class DuplicateCaliberCodeException extends RuntimeException{

    private final String code;

    public DuplicateCaliberCodeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
