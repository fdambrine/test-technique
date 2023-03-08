package com.wiiisdom.hr.wp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * common error response
 */
@Schema(description = "Common structure for all errors the API sends back")
@Getter
@Setter
public class ErrorResponse {

    @Schema(description = "Human readable error message")
    private final String error;
    @Schema(description = "On http 500 error code, this will give you an error code to get more information on"
            + "360suite support website.")
    private final String code;

    /**
     * Initialize response with message
     *
     * @param error error message
     */
    public ErrorResponse(String error) {
        this(error, null);
    }

    /**
     * Initialize response with full data
     *
     * @param error error message
     * @param code error code
     */
    public ErrorResponse(String error, String code) {
        this.error = error;
        this.code = code;
    }

}
