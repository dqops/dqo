package com.dqops.metadata.sources.fileformat.csv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * New line character.
 */
public enum NewLineCharacterType {

    /**
     * The \r line delimiter.
     */
    @JsonProperty("cr")
    cr,

    /**
     * The \n line delimiter.
     */
    @JsonProperty("lf")
    lf,

    /**
     * The \r\n line delimiter.
     */
    @JsonProperty("crlf")
    crlf;

    @Override
    public String toString() {
        switch(this){
            case cr: return "\\r";
            case lf: return "\\n";
            case crlf: return "\\r\\n";
        }
        throw new RuntimeException("Unknown new line character : " + this);
    }
}
