/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.cli.terminal.ansi;

import org.springframework.stereotype.Service;

@Service
public class UrlFormatterImpl implements UrlFormatter {
    /**
     * Formats a String to an ANSI hyperlink format.
     *
     * @param urlText     URL to be linked.
     * @param visibleText Text to be displayed when hyperlinked.
     */
    @Override
    public String getUrlAnsiString(String urlText, String visibleText) {
        AnsiCode ansiCode = AnsiCode.hyperlink;
        return ansiCode.on() + urlText + AnsiCode.CUTOFF +
                visibleText + ansiCode.off() + AnsiCode.CUTOFF;
    }
}
