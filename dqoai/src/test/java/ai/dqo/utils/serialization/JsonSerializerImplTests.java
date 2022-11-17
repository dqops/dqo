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
package ai.dqo.utils.serialization;

import ai.dqo.BaseTest;
import ai.dqo.execution.rules.HistoricDataPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootTest
public class JsonSerializerImplTests extends BaseTest {
    private JsonSerializerImpl sut;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.sut = new JsonSerializerImpl();
    }

    @Test
    void serialize_whenObjectGiven_thenIsSerializedToJson() {
        YamlTestable testable = new YamlTestable();
        testable.setField1("aaa");

        String json = this.sut.serialize(testable);

        Assertions.assertEquals("{\"field1\":\"aaa\",\"int1\":0}", json);
    }

    @Test
    void deserialize_whenJsonGiven_thenDeserializesString() {
        YamlTestable deserialized = this.sut.deserialize("{\"field1\":\"aaa\",\"int1\":10}", YamlTestable.class);
        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals("aaa", deserialized.getField1());
        Assertions.assertEquals(10, deserialized.getInt1());
    }

    @Test
    void deserialize_whenJsonGivenAndObjectImplementsDeserializableAware_thenObjectIsNotifiedOfDeserialization() {
        YamlTestable deserialized = this.sut.deserialize("{\"field1\":\"aaa\",\"int1\":10}", YamlTestable.class);
        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals("aaa", deserialized.getField1());
        Assertions.assertEquals(10, deserialized.getInt1());
        Assertions.assertTrue(deserialized.wasOnDeserializedCalled);
    }

    @Test
    void deserializeMultiple_whenOneJsonGiven_thenReturnsThisOneJson() {
        byte[] dataBytes = "{\"field1\":\"aaa\",\"int1\":10}".getBytes(StandardCharsets.UTF_8);
        InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(dataBytes));
        List<YamlTestable> deserialized = this.sut.deserializeMultiple(inputStreamReader, YamlTestable.class);
        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals(1, deserialized.size());
        Assertions.assertEquals("aaa", deserialized.get(0).getField1());
        Assertions.assertEquals(10, deserialized.get(0).getInt1());
    }

    @Test
    void deserializeMultiple_whenTwoJsonGiven_thenReturnsThisOneJson() {
        byte[] dataBytes = "{\"field1\":\"aaa\",\"int1\":10}\n{\"field1\":\"bbb\",\"int1\":20}\n".getBytes(StandardCharsets.UTF_8);
        InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(dataBytes));
        List<YamlTestable> deserialized = this.sut.deserializeMultiple(inputStreamReader, YamlTestable.class);
        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals(2, deserialized.size());
        Assertions.assertEquals("aaa", deserialized.get(0).getField1());
        Assertions.assertEquals(10, deserialized.get(0).getInt1());
        Assertions.assertEquals("bbb", deserialized.get(1).getField1());
        Assertions.assertEquals(20, deserialized.get(1).getInt1());
    }

    @Test
    void serialize_whenHistoricDataPointWithDatesSerialized_thenDateFormatIsCorrect() {
        HistoricDataPoint dataPoint = new HistoricDataPoint();
        dataPoint.setBackPeriodsIndex(-2);
        dataPoint.setSensorReading(10.5);
        LocalDateTime localDateTime = LocalDateTime.of(2022, 03, 07, 22, 15, 10);
        dataPoint.setLocalDatetime(localDateTime);
        dataPoint.setTimestampUtc(localDateTime.toInstant(ZoneOffset.ofHours(2)));

        String json = this.sut.serialize(dataPoint);

        Assertions.assertEquals("""
                   {"timestamp_utc":"2022-03-07T20:15:10Z","local_datetime":"2022-03-07T22:15:10","back_periods_index":-2,"sensor_reading":10.5}""", json);
    }
}
