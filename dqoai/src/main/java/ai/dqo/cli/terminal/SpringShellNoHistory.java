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

import org.jline.reader.impl.history.DefaultHistory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A fix to stop saving Spring Shell log (spring-shell.log) which we don't use.
 */
@Component
public class SpringShellNoHistory extends DefaultHistory {
    /**
     * Does nothing, just captures saving the spring-shell.log.
     * @throws IOException
     */
    @Override
    public void save() throws IOException {
    }
}
