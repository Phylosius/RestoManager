package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponseRest {
    private String message;
    private Exception error;

    public ErrorResponseRest(Exception exception) {
        this(exception.getMessage(), exception);
    }
}
