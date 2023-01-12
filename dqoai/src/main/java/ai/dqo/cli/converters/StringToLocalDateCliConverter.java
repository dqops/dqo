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

package ai.dqo.cli.converters;

import ai.dqo.utils.datetime.StringToLocalDateConverter;
import picocli.CommandLine.ITypeConverter;

import java.time.LocalDate;

public class StringToLocalDateCliConverter implements ITypeConverter<LocalDate> {
    @Override
    public LocalDate convert(String value) {
        LocalDate result = StringToLocalDateConverter.convert(value);
        if (result == null) {
            throw new IllegalArgumentException("Incorrect date format (expected yyyy.MM or yyyy.MM.dd)");
        }
        return result;
    }
}
