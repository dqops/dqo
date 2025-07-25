/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.dqops.BaseTest;
import com.dqops.execution.rules.HistoricDataPoint;
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

    @BeforeEach
    void setUp() {
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
        LocalDateTime localDateTime = LocalDateTime.of(2022, 03, 07, 22, 15, 10);
        HistoricDataPoint dataPoint = new HistoricDataPoint(localDateTime.toInstant(ZoneOffset.ofHours(2)), localDateTime, -2, 10.5, null);
        dataPoint.setBackPeriodsIndex(-2);
        dataPoint.setSensorReadout(10.5);

        String json = this.sut.serialize(dataPoint);

        Assertions.assertEquals("""
                   {"timestamp_utc_epoch":1646684110,"local_datetime_epoch":1646691310,"back_periods_index":-2,"sensor_readout":10.5}""", json);
    }
}
