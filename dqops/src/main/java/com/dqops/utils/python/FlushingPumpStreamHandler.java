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
