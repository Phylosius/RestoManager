package hei.phylosius.restomanager.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseRest {
    private String message;
    private Exception error;

    public ErrorResponseRest(Exception exception) {
        this(exception.getMessage(), exception);
    }
}
