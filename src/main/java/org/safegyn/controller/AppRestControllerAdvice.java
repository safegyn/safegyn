package org.safegyn.controller;

import org.apache.log4j.Logger;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.error.ErrorData;
import org.safegyn.model.error.FieldErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppRestControllerAdvice {

    private static final Logger logger = Logger.getLogger(AppRestControllerAdvice.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public ErrorData handleUnknownException(HttpServletRequest req, Throwable t) {
        t.setStackTrace(setStackTrace(t.getStackTrace()));
        logger.error("Internal Server Error : " + t.getMessage() + " " + req.getRequestURI(), t);
        ErrorData data = new ErrorData();
        data.setCode(ApiException.Type.SERVER_ERROR);
        data.setMessage("Internal error");
        data.setDescription(fromThrowable(t));
        return data;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public ErrorData handleBindException(HttpServletRequest req, BindException e) {
        return handleBindingResult(e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ErrorData handleArgumentException(HttpServletRequest req, MethodArgumentNotValidException e) {
        return handleBindingResult(e.getBindingResult());
    }

    public static ErrorData handleBindingResult(BindingResult br) {
        List<FieldErrorData> errors = new ArrayList();
        Iterator var2 = br.getFieldErrors().iterator();

        while (var2.hasNext()) {
            FieldError ferror = (FieldError) var2.next();
            FieldErrorData fdata = new FieldErrorData();
            fdata.setCode(ferror.getCode());
            fdata.setField(ferror.getField());
            fdata.setMessage(ferror.getDefaultMessage());
            errors.add(fdata);
        }

        ErrorData data = new ErrorData();
        data.setCode(ApiException.Type.USER_ERROR);
        data.setMessage("Bad input provided");
        data.setErrors(errors);
        return data;
    }

    public static String fromThrowable(Throwable t) {
        if (t == null) {
            return null;
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return sw.toString();
        }
    }


    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ErrorData handleApiException(HttpServletRequest req, ApiException e, HttpServletResponse response) {

        ApiException.Type status = e.getType();
        setResponseStatus(response, status);

        e.setStackTrace(setStackTrace(e.getStackTrace()));

        if (e.getType().equals(ApiException.Type.SERVER_ERROR))
            logger.error("ApiException : " + e.getMessage(), e);
        else
            logger.debug("ApiException : " + e.getMessage(), e);

        ErrorData data = new ErrorData();
        data.setCode(e.getType());
        data.setMessage(e.getMessage());
        data.setDescription(fromThrowable(e.getCause()));
        data.setErrors(e.getErrors());
        return data;
    }

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class, OptimisticLockException.class})
    @ResponseBody
    public ErrorData handleOptimisticLockingException(Exception e, HttpServletResponse response) {
        e.setStackTrace(setStackTrace(e.getStackTrace()));
        logger.error("OptimisticLockingException : " + e.getMessage(), e);

        ErrorData errorData = new ErrorData();
        errorData.setMessage("Please try again");
        errorData.setCode(ApiException.Type.SERVER_ERROR);
        errorData.setDescription(fromThrowable(e.getCause()));
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return errorData;
    }

    private void setResponseStatus(HttpServletResponse response, ApiException.Type status) {
        if (status.equals(ApiException.Type.SERVER_ERROR))
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        else
            response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    private static StackTraceElement[] setStackTrace(StackTraceElement[] stackTraceElements) {

        List<StackTraceElement> stackList = Arrays.stream(stackTraceElements)
                .filter(stackTraceElement -> String.valueOf(stackTraceElement).startsWith("com.nextscm"))
                .collect(Collectors.toList());
        return stackList.toArray(new StackTraceElement[stackList.size()]);
    }

}


