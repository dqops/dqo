package com.dqops.data.errorsamples.services;

import java.time.LocalDateTime;

/**
 * File name for an error samples CSV file creator.
 */
public interface ErrorSamplesFileNameCreator {

    /**
     * Creates a file name for an error samples CSV file.
     * @param details ErrorSamplesFileNameDetails with names used when creating a file name.
     * @param dateTime Date time added to the end of the file name.
     * @return The file name.
     */
    String createFileName(ErrorSamplesFileNameDetails details, LocalDateTime dateTime);
}
