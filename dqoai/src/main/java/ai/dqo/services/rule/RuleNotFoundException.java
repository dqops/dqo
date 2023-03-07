package ai.dqo.services.rule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RuleNotFoundException extends RuntimeException {
    public RuleNotFoundException(String message) {
        super(message);
    }
}
