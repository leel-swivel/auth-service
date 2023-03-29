package com.hilltop.authservice.wrapper;

import com.hilltop.authservice.domain.response.ResponseDto;
import com.hilltop.authservice.enums.ResponseStatusType;
import com.hilltop.authservice.enums.SuccessResponseStatusType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * SuccessResponseWrapper
 */
@Getter
public class SuccessResponseWrapper extends ResponseWrapper {

    private final int statusCode;

    public SuccessResponseWrapper(ResponseStatusType status, SuccessResponseStatusType successResponseStatusType,
                                  ResponseDto responseDto, String displayMessage, HttpStatus httpStatus) {
        super(status, successResponseStatusType.getMessage(), responseDto, displayMessage);
        this.statusCode = httpStatus.value();
    }
}