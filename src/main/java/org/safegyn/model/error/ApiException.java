package org.safegyn.model.error;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;

    private Type type;
    private String context;
    private String problem;
    private String solution;
    private Collection<FieldErrorData> errors;

    public ApiException(Type type, String context, String problem, String solution) {
        super("A " + type.name() + " has occurred!\n " + context + "\n " + problem + "\n " + solution);
        this.type = type;
        this.context = context;
        this.problem = problem;
        this.solution = solution;
    }

    public enum Type {
        SERVER_ERROR,
        USER_ERROR
    }

}
