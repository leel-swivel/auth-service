package com.hilltop.authservice.enums;

/**
 * ResponseStatusType
 */
public enum ResponseStatusType {

    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private final String status;

    ResponseStatusType(String status) {
        this.status = status;
    }

}