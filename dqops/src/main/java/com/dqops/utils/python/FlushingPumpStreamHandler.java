/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.python;

import org.apache.commons.exec.PumpStreamHandler;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Pumping stream that flushes any outputs.
 */
public class FlushingPumpStreamHandler extends PumpStreamHandler {
    public FlushingPumpStreamHandler() {
    }

    public FlushingPumpStreamHandler(OutputStream outAndErr) {
        super(outAndErr);
    }

    public FlushingPumpStreamHandler(OutputStream out, OutputStream err) {
        super(out, err);
    }

    public FlushingPumpStreamHandler(OutputStream out, OutputStream err, InputStream input) {
        super(out, err, input);
    }

    @Override
    protected void createProcessOutputPump(InputStream is, OutputStream os) {
        super.createProcessOutputPump(is, new FlushingOutputStream(os));
    }

    @Override
    public void setProcessInputStream(OutputStream os) {
        super.setProcessInputStream(new FlushingOutputStream(os));
    }
}
