package org.safegyn.model.error;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class ErrorData {

    private ApiException.Type code;
    private String message;
    private String description;
    private Collection<FieldErrorData> errors;

    public ErrorData() {
        setCode(ApiException.Type.SERVER_ERROR);
        setErrors(new ArrayList<FieldErrorData>(0));
    }

}
