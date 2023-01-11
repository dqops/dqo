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
package ai.dqo.utils.datetime;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@SpringBootTest
public class TimeZoneUtilityTests extends BaseTest {

    @Test
    void parseZoneId_whenArgIsNull_thenReturnUTC() {
        Assertions.assertEquals(ZoneId.of("UTC"), TimeZoneUtility.parseZoneId(null));
    }

    @Test
    void parseZoneId_whenArgIsEmpty_thenReturnUTC() {
        Assertions.assertEquals(ZoneId.of("UTC"), TimeZoneUtility.parseZoneId(""));
    }

    @Test
    void parseZoneId_whenArgIsValid_thenReturnCorrectInstanceForTimezone() {
        List<String> zoneIds = new ArrayList<>(ZoneId.getAvailableZoneIds());
        List<ZoneId> expectedResult = new ArrayList<>();
        List<ZoneId> obtainedResult = new ArrayList<>();

        for (String id : zoneIds) {
            try {
                ZoneId zoneId = ZoneId.of(id);
                expectedResult.add(zoneId);
            } catch (Exception ex) {
                ZoneId zoneIdShort = ZoneId.of(id, ZoneId.SHORT_IDS);
                expectedResult.add(zoneIdShort);
            }
            obtainedResult.add(TimeZoneUtility.parseZoneId(id));
        }

        Assertions.assertEquals(expectedResult, obtainedResult);
    }

    @Test
    void parseTimeOneOrDefault_whenArgIsValid_thenReturnCorrectTimezone() {
        List<String> zoneIds = new ArrayList<>(ZoneId.getAvailableZoneIds());
        List<TimeZone> expectedResult = new ArrayList<>();
        List<TimeZone> obtainedResult = new ArrayList<>();
        List<ZoneId> zoneInstances = new ArrayList<>();

        for (String id : zoneIds) {
            zoneInstances.add(TimeZoneUtility.parseZoneId(id));
            obtainedResult.add(TimeZoneUtility.parseTimeOneOrDefault(id));
        }
        for (ZoneId instance : zoneInstances) {
            try {
                expectedResult.add(TimeZone.getTimeZone(instance));
            } catch (Exception ex) {
                expectedResult.add(TimeZone.getTimeZone("UTC"));
            }
        }

        Assertions.assertEquals(expectedResult, obtainedResult);
    }

    @Test
    void getAvailableZoneIds_whenCall_thenReturnAvailableZoneIdsList() {
        List<String> expectedResult = new ArrayList<>(ZoneId.getAvailableZoneIds());
        Assertions.assertEquals(expectedResult, TimeZoneUtility.getAvailableZoneIds());
    }

}
