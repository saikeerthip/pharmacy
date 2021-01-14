package com.galaxy.exception;

import com.galaxy.model.PrescriptionErrorResponse;
import com.galaxy.model.PrescriptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@ControllerAdvice
@Slf4j
public class PrescriptionExceptionHandler {

    private static final String LOG_EX_MESSAGE = "An exception occurred :";

    @ExceptionHandler(PrescriptionAccessException.class)
    @ResponseStatus(value = UNAUTHORIZED)
    @ResponseBody
    public PrescriptionErrorResponse handleAuthenticationException(HttpServletRequest httpServletRequest, PrescriptionAccessException ex) {

        log.debug("An invalid creds error occurred :", ex);
        PrescriptionErrorResponse errorResponse = new PrescriptionErrorResponse();
        errorResponse.setTimeStamp(currentTimeMillis());
        errorResponse.setStatus(ex.getErrorCode());
        errorResponse.setError(ex.getErrorCode());
        errorResponse.setException(PrescriptionAccessException.class.getName());
        errorResponse.setErrorCode("PRESCRIPTION_ERROR_401");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(httpServletRequest.getServletPath());
        log.debug("The invalid Creds error response  :{}", errorResponse);
        return errorResponse;
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(value = BAD_REQUEST)
    @ResponseBody
    public PrescriptionErrorResponse handleInvalidRequestException(HttpServletRequest httpServletRequest, MethodArgumentNotValidException ex) {

        log.error(LOG_EX_MESSAGE, ex);
        List<String> errorRequestAttributes = processFailures(ex);
        PrescriptionErrorResponse errorResponse = new PrescriptionErrorResponse();
        errorResponse.setTimeStamp(currentTimeMillis());
        errorResponse.setStatus(valueOf(BAD_REQUEST));
        errorResponse.setError(BAD_REQUEST.name());
        errorResponse.setException(PrescriptionException.class.getName());
        errorResponse.setErrorCode("PRESCRIPTION_ERROR_400");
        errorResponse.setMessage("Request is missing attributes: " + errorRequestAttributes);
        errorResponse.setPath(httpServletRequest.getServletPath());
        return errorResponse;
    }

    @ExceptionHandler(PrescriptionException.class)
    @ResponseBody
    public ResponseEntity<PrescriptionResponse> handleServiceException(HttpServletRequest httpServletRequest, PrescriptionException ex) {
        log.error(LOG_EX_MESSAGE, ex);
        PrescriptionResponse prescriptionResponse = new PrescriptionResponse();
        prescriptionResponse.setStatus(ex.getErrorCode());
        prescriptionResponse.setMessage(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(prescriptionResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<PrescriptionErrorResponse> handleException(HttpServletRequest httpServletRequest, Exception ex) {
        log.error(LOG_EX_MESSAGE, ex);
        PrescriptionErrorResponse prescriptionErrorResponse = new PrescriptionErrorResponse();
        prescriptionErrorResponse.setTimeStamp(currentTimeMillis());
        prescriptionErrorResponse.setStatus(valueOf(INTERNAL_SERVER_ERROR.value()));
        prescriptionErrorResponse.setError(INTERNAL_SERVER_ERROR.toString());
        prescriptionErrorResponse.setException(ex.getClass().getName());
        prescriptionErrorResponse.setPath(httpServletRequest.getServletPath());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(prescriptionErrorResponse);
    }

    private List<String> processFailures(MethodArgumentNotValidException ex) {

        List<String> errorRequestAttributes = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        if (!isEmpty(result.getFieldErrors())) {
            log.error("Field errors :{}", result.getFieldErrors());
            result.getFieldErrors().forEach(e ->
                    errorRequestAttributes.add(e.getField()));
        }
        //process if any custom errors present
        if (isEmpty(errorRequestAttributes) && !isEmpty(result.getAllErrors())) {
            errorRequestAttributes.add(result.getAllErrors().get(0).getDefaultMessage());
        }
        log.error("Final validation errors :{}", errorRequestAttributes);
        return errorRequestAttributes.stream().distinct().collect(toList());
    }

}
