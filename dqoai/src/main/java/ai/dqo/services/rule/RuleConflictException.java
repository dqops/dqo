package ai.dqo.services.rule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RuleConflictException extends RuntimeException {
    public RuleConflictException(String message) {
        super(message);
    }
}