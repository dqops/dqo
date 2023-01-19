/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.terminal;

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;


/**
 * Console terminal wrapper. Provides access to the terminal services.
 */
public class TerminalWriterImpl extends TerminalWriterAbstract {
    private final Terminal terminal;

    public TerminalWriterImpl(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Writes a text to the terminal without moving to the next line.
     * @param text Text to be written.
     */
    @Override
    public void write(String text) {
		this.terminal.writer().write(text);
		this.terminal.flush();
    }

    /**
     * Clears the screen.
     */
    @Override
    public void clearScreen() {
        this.terminal.puts(InfoCmp.Capability.clear_screen);
        this.terminal.flush();
    }

    /**
     * Gets terminal width.
     * @return Terminal width.
     */
    @Override
    public Integer getTerminalWidth() {
        return this.terminal.getWidth();
    }

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    @Override
    public Integer getTerminalHeight() {
        return this.terminal.getHeight();
    }
}
