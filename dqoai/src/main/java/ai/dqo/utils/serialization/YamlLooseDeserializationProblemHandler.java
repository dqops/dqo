package ai.dqo.utils.serialization;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import java.io.IOException;

/**
 * Deserialization problem handler that ignores invalid numbers.
 */
public class YamlLooseDeserializationProblemHandler extends DeserializationProblemHandler {
    @Override
    public Object handleWeirdNumberValue(DeserializationContext ctxt, Class<?> targetType, Number valueToConvert, String failureMsg) throws IOException {
        if (targetType == int.class) {
            return 0;
        }

        if (targetType == long.class) {
            return 0L;
        }

        if (targetType == double.class) {
            return 0.0;
        }

        return null;
    }
}
