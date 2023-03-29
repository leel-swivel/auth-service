package com.hilltop.authservice.domain.response;

import com.hilltop.authservice.domain.BaseDto;

/**
 * ResponseDto
 */
public abstract class ResponseDto implements BaseDto {
    @Override
    public String toLogJson() {
        return toJson();
    }
}
