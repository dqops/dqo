package com.dqops.metadata.sources.fileformat.csv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * New line character.
 */
public enum NewLineCharacterType {
    @JsonProperty("\\r")
    CR,

    @JsonProperty("\\n")
    LF,

    @JsonProperty("\\r\\n")
    CRLF;

    @Override
    public String toString() {
        switch(this){
            case CR: return "\\r";
            case LF: return "\\n";
            case CRLF: return "\\r\\n";
        }
        throw new RuntimeException("Unknown new line character : " + this);
    }
}
