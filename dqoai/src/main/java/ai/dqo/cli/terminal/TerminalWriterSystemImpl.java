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

/**
 * Console terminal wrapper. Provides access to the terminal services.
 * Implementation relying on the standard output (StdOut).
 */
public class TerminalWriterSystemImpl extends TerminalWriterAbstract {

    private Integer terminalWidth;

    public TerminalWriterSystemImpl(Integer terminalWidth) {
        this.terminalWidth = terminalWidth;
    }

    /**
     * Writes a text to the terminal without moving to the next line.
     * @param text Text to be written.
     */
    @Override
    public void write(String text) {
        if (text != null) {
            System.out.print(text);
            System.out.flush();
        }
    }

    /**
     * Writes a URL to the terminal, formatted as a hyperlink if possible.
     *
     * @param url URL to be linked.
     * @param text Text to be displayed if hyperlinked.
     */
    @Override
    public void writeUrl(String url, String text) {
        write(text + " {" + url + "}");
    }

    /**
     * Clears the screen.
     */
    public void clearScreen() {
    }

    /**
     * Gets terminal width.
     * @return Terminal width.
     */
    @Override
    public Integer getTerminalWidth() {
        return this.terminalWidth;
    }

    /**
     * Gets terminal height.
     * @return Terminal height.
     */
    public Integer getTerminalHeight() {
        return Integer.MAX_VALUE;
    }
}
