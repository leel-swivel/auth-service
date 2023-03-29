package com.hilltop.authservice.controller;


import com.hilltop.authservice.config.Translator;
import com.hilltop.authservice.domain.response.ResponseDto;
import com.hilltop.authservice.enums.ErrorResponseStatusType;
import com.hilltop.authservice.enums.ResponseStatusType;
import com.hilltop.authservice.enums.SuccessResponseStatusType;
import com.hilltop.authservice.wrapper.ErrorResponseWrapper;
import com.hilltop.authservice.wrapper.ResponseWrapper;
import com.hilltop.authservice.wrapper.SuccessResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Controller {
    protected static final int DEFAULT_PAGE = 0;
    protected static final int PAGE_MAX_SIZE = 250;
    protected static final String DEFAULT_SORT = "updatedAt";

    protected final Translator translator;

    @Autowired
    public Controller(Translator translator) {
        this.translator = translator;
    }

    /**
     * This method creates the data response for success request.
     *
     * @param responseDto responseDto
     * @return response entity
     */
    protected ResponseEntity<ResponseWrapper> getSuccessResponse(ResponseDto responseDto,
                                                                 SuccessResponseStatusType successResponseStatusType, HttpStatus httpStatus) {

        var successResponseWrapper = new SuccessResponseWrapper(ResponseStatusType.SUCCESS,
                successResponseStatusType, responseDto,
                translator.toLocale(successResponseStatusType.getCodeString(successResponseStatusType.getCode())),httpStatus);


        return new ResponseEntity<>(successResponseWrapper, httpStatus);
    }

    /**
     * This method creates the internal server error response.
     *
     * @return response entity
     */
    protected ResponseEntity<ResponseWrapper> getInternalServerError() {
        var errorResponseWrapper = new ErrorResponseWrapper(ResponseStatusType.ERROR,
                ErrorResponseStatusType.INTERNAL_SERVER_ERROR.getMessage(), null,
                translator.toLocale(ErrorResponseStatusType.
                        getCodeString(ErrorResponseStatusType.INTERNAL_SERVER_ERROR.getCode())),
                ErrorResponseStatusType.INTERNAL_SERVER_ERROR.getCode());
        return new ResponseEntity<>(errorResponseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method creates the empty data response for bad request.
     *
     * @param errorsResponseStatusType errorResponseStatusType
     * @return bad request error response
     */
    protected ResponseEntity<ResponseWrapper> getErrorResponse(ErrorResponseStatusType errorsResponseStatusType) {
        var errorResponseWrapper = new ErrorResponseWrapper(ResponseStatusType.ERROR,
                errorsResponseStatusType.getMessage(), null,
                translator.toLocale(ErrorResponseStatusType.getCodeString(errorsResponseStatusType.getCode())),
                errorsResponseStatusType.getCode());
        return new ResponseEntity<>(errorResponseWrapper, HttpStatus.BAD_REQUEST);
    }
}
