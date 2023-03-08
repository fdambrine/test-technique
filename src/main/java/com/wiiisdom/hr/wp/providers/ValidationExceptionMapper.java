package com.wiiisdom.hr.wp.providers;

import java.util.stream.Collectors;

import com.wiiisdom.hr.wp.response.ErrorResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Transform constraint violation into error 400 + error payload
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getConstraintViolations().stream().map(
                        ConstraintViolation::getMessage).collect(Collectors.joining(System.lineSeparator()))))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
