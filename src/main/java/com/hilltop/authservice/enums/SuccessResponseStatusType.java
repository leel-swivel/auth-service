package com.hilltop.authservice.enums;

import lombok.Getter;

/**
 * SuccessResponseStatusType
 */
@Getter
public enum SuccessResponseStatusType {

    CREATE_USER(2000, "Successfully registered the user."),
    GENERATE_TOKEN(2001,"Successfully returned the token."),
    VALIDATE_TOKEN(2002, "Successfully validated the token.");

    private final int code;
    private final String message;

    SuccessResponseStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Success code covert into string to read display message from success property file
     *
     * @param successCode successCode
     * @return string code
     */
    public String getCodeString(int successCode) {
        return Integer.toString(successCode);
    }
}
