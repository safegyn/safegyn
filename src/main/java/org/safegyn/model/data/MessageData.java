package org.safegyn.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageData {

    private String type;

    private String context;

    private String problem;

    private String solution;

    private String message;

}
