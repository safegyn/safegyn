package org.safegyn.model.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorData {

    private String field;
    private String code;
    private String message;

}
