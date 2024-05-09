/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.connectors.duckdb.schema;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Parser that is able to parse data types from DuckDB.
 */
@Component
public class DuckDBDataTypeParserImpl implements DuckDBDataTypeParser {
    /**
     * Parses a text in the <code>dataType</code> into an object that describes the field. Parses also structures and arrays.
     * @param dataType Data type to parse.
     * @param fieldName Field name to store in the result object (because the root data type has no field name, only nested fields contain a name).
     * @return Data type, also for nested structures.
     */
    public DuckDBField parseFieldType(String dataType, String fieldName) {
        Reader textReader = new StringReader(dataType);
        StreamTokenizer tokenizer = new StreamTokenizer(textReader);
        tokenizer.eolIsSignificant(false);
        tokenizer.ordinaryChar('(');
        tokenizer.ordinaryChar(')');
        tokenizer.ordinaryChar('[');
        tokenizer.ordinaryChar(']');
        tokenizer.ordinaryChar(',');
        tokenizer.wordChars('"', '"');
        tokenizer.wordChars('_', '_');
        tokenizer.parseNumbers();

        try {
            DuckDBField field = parseDataTypeFromTokenizer(tokenizer, fieldName);
            return field;
        }
        catch (Exception ex) {
            throw new TypeParserUnexpectedTokenException("Cannot parse the data type: " + dataType + ", error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Parses a data type, knowing the field type.
     * @param tokenizer Source tokenizer.
     * @param fieldName Field name.
     * @return Field data type.
     */
    public DuckDBField parseDataTypeFromTokenizer(StreamTokenizer tokenizer, String fieldName) {
        DuckDBField field = new DuckDBField(fieldName);

        try {
            int nextToken = tokenizer.nextToken();
            if (nextToken == StreamTokenizer.TT_WORD) {
                String word = tokenizer.sval.toUpperCase(Locale.ROOT);
                field.setTypeName(word);

                if (Objects.equals(word, "TIMESTAMP")) {
                    String _with = parseNextTextToken(tokenizer);
                    if (Objects.equals(_with, "WITH")) {
                        field.setTypeName(field.getTypeName() + " " + _with);
                        String _time = parseNextTextToken(tokenizer);
                        field.setTypeName(field.getTypeName() + " " + _time);
                        String _zone = parseNextTextToken(tokenizer);
                        field.setTypeName(field.getTypeName() + " " + _zone);
                    } else {
                        tokenizer.pushBack();
                    }
                }
                else if (Objects.equals(word, "STRUCT") || Objects.equals(word, "UNION")) {
                    field.setStruct(true);
                    ArrayList<DuckDBField> nestedFields = new ArrayList<>();
                    field.setNestedFields(nestedFields);
                    nextToken = tokenizer.nextToken();
                    if (nextToken != '(') {
                        throw new TypeParserUnexpectedTokenException("STRUCT not configured: " + tokenizer);
                    }

                    nextToken = tokenizer.nextToken();
                    while (tokenizer.ttype != StreamTokenizer.TT_EOF) {
                        String nestedFieldName = null;
                        if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                            nestedFieldName = tokenizer.sval;
                            if (nestedFieldName.length() > 2 && nestedFieldName.startsWith("'") || nestedFieldName.endsWith("'")) {
                                nestedFieldName = nestedFieldName.substring(1, nestedFieldName.length() - 1);
                            } else if (nestedFieldName.length() > 2 && nestedFieldName.startsWith("\"") || nestedFieldName.endsWith("\"")) {
                                nestedFieldName = nestedFieldName.substring(1, nestedFieldName.length() - 1);
                            }
                        }
                        else if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                            nestedFieldName = Double.toString(tokenizer.nval);
                        } else {
                            throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                        }

                        DuckDBField nestedField = parseDataTypeFromTokenizer(tokenizer, nestedFieldName);
                        nestedFields.add(nestedField);

                        if (tokenizer.ttype == ')') {
                            break; // end of field list inside a struct
                        } else if (tokenizer.ttype != ',') {
                            throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer); // should be the next field
                        }

                        nextToken = tokenizer.nextToken(); // eat the last token
                    }
                } else if (Objects.equals(word, "MAP")) {
                    nextToken = tokenizer.nextToken();

                    if (nextToken == '(') {
                        DuckDBField keyField = parseDataTypeFromTokenizer(tokenizer, "key");
                        if (tokenizer.ttype != ',') {
                            throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                        }

                        DuckDBField valueField = parseDataTypeFromTokenizer(tokenizer, "value");

                        if (tokenizer.ttype != ')') {
                            throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                        }

                        field.setMap(true);
                        field.setNestedFields(List.of(keyField, valueField));
                    } else {
                        throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                    }
                }

                nextToken = tokenizer.nextToken();
                if (nextToken == StreamTokenizer.TT_EOF) {
                    return field;
                }


                if (nextToken == '(') {
                    nextToken = tokenizer.nextToken();

                    Integer firstSize = (int)tokenizer.nval;
                    if (nextToken == StreamTokenizer.TT_NUMBER) {
                        firstSize = (int)tokenizer.nval;

                        nextToken = tokenizer.nextToken();
                        if (nextToken == ',') {
                            // two arguments
                            nextToken = tokenizer.nextToken();
                            if (nextToken == StreamTokenizer.TT_NUMBER) {
                                int secondSize = (int) tokenizer.nval;
                                field.setPrecision(firstSize);
                                field.setScale(secondSize);
                                nextToken = tokenizer.nextToken();
                                if (nextToken != ')') {
                                    throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                                }
                                nextToken = tokenizer.nextToken();
                            }
                            else {
                                throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                            }
                        } else {
                            field.setLength(firstSize);
                            if (tokenizer.ttype != ')') {
                                throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                            }
                            nextToken = tokenizer.nextToken();
                        }
                    } else {
                        throw new TypeParserUnexpectedTokenException("Unexpected token: " + tokenizer);
                    }
                }

                if (tokenizer.ttype == '[') {
                    nextToken = tokenizer.nextToken();
                    Integer arrayLength = null;
                    if (nextToken == StreamTokenizer.TT_NUMBER) {
                        arrayLength = (int)tokenizer.nval;
                        nextToken = tokenizer.nextToken();
                    }

                    if (nextToken == ']') {
                        nextToken = tokenizer.nextToken();
                    } else {
                        throw new TypeParserUnexpectedTokenException("Expecting an end of array when parsing a data type token: " + tokenizer);
                    }

                    field.setArray(true);
                }

                // parse remaining tokens, detect NOT NULL, but stops at a comma
                while (tokenizer.ttype != StreamTokenizer.TT_EOF) {
                    if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                        String currentWordToken = tokenizer.sval.toUpperCase(Locale.ROOT);
                        if (Objects.equals(currentWordToken, "NOT")) {
                            String nextTextToken = parseNextTextToken(tokenizer);
                            if (Objects.equals(nextTextToken, "NULL")) {
                                field.setNullable(false);
                            }
                        }
                    } else if (tokenizer.ttype == ',') {
                        break;
                    } else if (tokenizer.ttype == ')') {
                        break;
                    } else if (tokenizer.ttype == '(') {
                        consumeAllUntilClosingParenthesis(tokenizer);
                    }

                    tokenizer.nextToken();
                }
            }

            return field;
        }
        catch (IOException ioe) {
            throw new TypeParserUnexpectedTokenException(ioe.getMessage(), ioe);
        }
    }

    /**
     * Consumes all tokens from the current token, which should be '(', until the closing ')'
     * @param tokenizer Tokenizer to consume.
     */
    private void consumeAllUntilClosingParenthesis(StreamTokenizer tokenizer) {
        try {
            while (tokenizer.ttype == StreamTokenizer.TT_WORD || tokenizer.ttype == StreamTokenizer.TT_NUMBER || tokenizer.ttype == ')') {
                if (tokenizer.ttype == ')') {
                    tokenizer.nextToken();
                    return;
                }

                tokenizer.nextToken();
            }
        }
        catch (IOException ioe) {
            throw new TypeParserUnexpectedTokenException("Failed to consume tokens", ioe);
        }
    }

    /**
     * Blindly parses the next word.
     * @param tokenizer Tokenizer.
     * @return Word that was parsed.
     */
    private String parseNextTextToken(StreamTokenizer tokenizer) {
        try {
            if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
                return null;
            }

            int nextToken = tokenizer.nextToken();
            if (nextToken == StreamTokenizer.TT_WORD) {
                String word = tokenizer.sval.toUpperCase(Locale.ROOT);
                return word;
            }

            if (tokenizer.ttype != StreamTokenizer.TT_WORD && tokenizer.ttype != StreamTokenizer.TT_EOF) {
                throw new TypeParserUnexpectedTokenException("Unexpected token");
            }

            return null;
        }
        catch (IOException ioe) {
            throw new TypeParserUnexpectedTokenException(ioe.getMessage(), ioe);
        }
    }

    /**
     * Blindly parses the next word.
     * @param tokenizer Tokenizer.
     * @return Word that was parsed.
     */
    private Integer parseNextIntegerToken(StreamTokenizer tokenizer) {
        try {
            if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
                return null;
            }

            int nextToken = tokenizer.nextToken();
            if (nextToken == StreamTokenizer.TT_NUMBER) {
                return (int)tokenizer.nval;
            }

            if (tokenizer.ttype != StreamTokenizer.TT_NUMBER && tokenizer.ttype != StreamTokenizer.TT_EOF) {
                throw new TypeParserUnexpectedTokenException("Unexpected token");
            }

            return null;
        }
        catch (IOException ioe) {
            throw new TypeParserUnexpectedTokenException(ioe.getMessage(), ioe);
        }
    }
}
