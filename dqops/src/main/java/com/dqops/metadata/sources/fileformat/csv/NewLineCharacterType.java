/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

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
