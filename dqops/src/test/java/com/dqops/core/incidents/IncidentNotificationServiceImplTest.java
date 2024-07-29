package com.dqops.core.incidents;

import com.dqops.BaseTest;
import com.dqops.core.incidents.message.IncidentNotificationMessage;
import com.dqops.core.incidents.message.MessageAddressPair;
import com.dqops.core.incidents.message.SampleIncidentMessages;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.FilteredNotificationSpecMap;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.incidents.NotificationFilterSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IncidentNotificationServiceImplTest extends BaseTest {

    private IncidentNotificationServiceImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = (IncidentNotificationServiceImpl)IncidentNotificationServiceObjectMother.getInstance();
    }

    @Test
    void filterNotifications_onDefaultNotificationWithOpenStatus_prepareThatNotification() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        List<MessageAddressPair> messageAddressPairs = sut.filterNotifications(message, notificationSpec);

        assertEquals(1, messageAddressPairs.size());
        assertEquals("default@email.com", messageAddressPairs.get(0).getNotificationAddress());
    }

    @Test
    void filterNotifications_onEmptyIncidentNotificationSpec_singlePairWithEmptyAddress() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();

        List<MessageAddressPair> messageAddressPairs = sut.filterNotifications(message, notificationSpec);

        assertEquals(1, messageAddressPairs.size());
        assertEquals("", messageAddressPairs.get(0).getNotificationAddress());
    }

    @Test
    void filterNotifications_onlyOneMatchingWithProcessAdditionalFilters_preparesItAndTheDefaultInOrder() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec();
        filteredNotificationSpec.setNotificationTarget(new IncidentNotificationSpec(){{
            setIncidentOpenedAddresses("1_filtered@email.com");
        }});
        filteredNotificationSpec.setProcessAdditionalFilters(true);
        filteredNotificationSpec.setNotificationFilter(new NotificationFilterSpec(){{
            setConnection("connection_name");
        }});


        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec();
        secondFilteredNotificationSpec.setNotificationTarget(new IncidentNotificationSpec(){{
            setIncidentOpenedAddresses("2_filtered@email.com");
        }});
        secondFilteredNotificationSpec.setProcessAdditionalFilters(true);
        secondFilteredNotificationSpec.setNotificationFilter(new NotificationFilterSpec(){{
            setConnection("connection_not_matching_name");
        }});


        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);

        notificationSpec.setFilteredNotificationMap(filteredNotificationSpecMap);

        List<MessageAddressPair> messageAddressPairs = sut.filterNotifications(message, notificationSpec);

        assertEquals(2, messageAddressPairs.size());
        assertEquals("1_filtered@email.com", messageAddressPairs.get(0).getNotificationAddress());
        assertEquals("default@email.com", messageAddressPairs.get(1).getNotificationAddress());
    }

    @Test
    void filterNotifications_threeMatchingAndDefaultWithPriority_returnedAllFourOrdered() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setProcessAdditionalFilters(true);
            setPriority(2);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setProcessAdditionalFilters(true);
            setPriority(3);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec thirdFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setProcessAdditionalFilters(true);
            setPriority(1);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("3_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);
        filteredNotificationSpecMap.put("third_filtered_notification", thirdFilteredNotificationSpec);

        notificationSpec.setFilteredNotificationMap(filteredNotificationSpecMap);

        List<MessageAddressPair> messageAddressPairs = sut.filterNotifications(message, notificationSpec);

        assertEquals(4, messageAddressPairs.size());
        assertEquals("3_filtered@email.com", messageAddressPairs.get(0).getNotificationAddress());
        assertEquals("1_filtered@email.com", messageAddressPairs.get(1).getNotificationAddress());
        assertEquals("2_filtered@email.com", messageAddressPairs.get(2).getNotificationAddress());
        assertEquals("default@email.com", messageAddressPairs.get(3).getNotificationAddress());
    }

    @Test
    void filterNotifications_oneMatchingHasSetProcessAdditionalFilters_returnsTwoWithValidOrder() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setProcessAdditionalFilters(true);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(2);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec thirdFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(3);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("3_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);
        filteredNotificationSpecMap.put("third_filtered_notification", thirdFilteredNotificationSpec);

        notificationSpec.setFilteredNotificationMap(filteredNotificationSpecMap);

        List<MessageAddressPair> messageAddressPairs = sut.filterNotifications(message, notificationSpec);

        assertEquals(2, messageAddressPairs.size());
        assertEquals("1_filtered@email.com", messageAddressPairs.get(0).getNotificationAddress());
        assertEquals("2_filtered@email.com", messageAddressPairs.get(1).getNotificationAddress());
    }

    @Test
    void filterNotifications_processAdditionalFiltersSetOnLowerPriority_returnsObjectWithTheHighestPriorityOnly() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(2);
            setProcessAdditionalFilters(true);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);

        notificationSpec.setFilteredNotificationMap(filteredNotificationSpecMap);

        List<MessageAddressPair> messageAddressPairs = sut.filterNotifications(message, notificationSpec);

        assertEquals(1, messageAddressPairs.size());
        assertEquals("1_filtered@email.com", messageAddressPairs.get(0).getNotificationAddress());
    }

    @Test
    void filterNotifications_commaSeparatedAddresses_returnedBoth() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setNotificationTarget(new IncidentNotificationSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com, 2_filtered@email.com");
            }});
            setNotificationFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);

        notificationSpec.setFilteredNotificationMap(filteredNotificationSpecMap);

        List<MessageAddressPair> messageAddressPairs = sut.filterNotifications(message, notificationSpec);

        assertEquals(2, messageAddressPairs.size());
        assertEquals("1_filtered@email.com", messageAddressPairs.get(0).getNotificationAddress());
        assertEquals("2_filtered@email.com", messageAddressPairs.get(1).getNotificationAddress());
    }

}