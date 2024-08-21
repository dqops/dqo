package com.dqops.core.incidents;

import com.dqops.BaseTest;
import com.dqops.core.incidents.message.IncidentNotificationMessage;
import com.dqops.core.incidents.message.IncidentNotificationMessageAddressPair;
import com.dqops.core.incidents.message.SampleIncidentMessages;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.metadata.incidents.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(1, incidentNotificationMessageAddressPairs.size());
        assertEquals("default@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
    }

    @Test
    void filterNotifications_onEmptyIncidentNotificationSpec_singlePairWithEmptyAddress() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(0, incidentNotificationMessageAddressPairs.size());
    }

    @Test
    void filterNotifications_onlyOneMatchingWithProcessAdditionalFilters_preparesItAndTheDefaultInOrder() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec();
        filteredNotificationSpec.setTarget(new IncidentNotificationTargetSpec(){{
            setIncidentOpenedAddresses("1_filtered@email.com");
        }});
        filteredNotificationSpec.setProcessAdditionalFilters(true);
        filteredNotificationSpec.setFilter(new NotificationFilterSpec(){{
            setConnection("connection_name");
        }});


        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec();
        secondFilteredNotificationSpec.setTarget(new IncidentNotificationTargetSpec(){{
            setIncidentOpenedAddresses("2_filtered@email.com");
        }});
        secondFilteredNotificationSpec.setProcessAdditionalFilters(true);
        secondFilteredNotificationSpec.setFilter(new NotificationFilterSpec(){{
            setConnection("connection_not_matching_name");
        }});


        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);

        notificationSpec.setFilteredNotifications(filteredNotificationSpecMap);

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(2, incidentNotificationMessageAddressPairs.size());
        assertEquals("1_filtered@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
        assertEquals("default@email.com", incidentNotificationMessageAddressPairs.get(1).getNotificationAddress());
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
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setProcessAdditionalFilters(true);
            setPriority(3);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec thirdFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setProcessAdditionalFilters(true);
            setPriority(1);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("3_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);
        filteredNotificationSpecMap.put("third_filtered_notification", thirdFilteredNotificationSpec);

        notificationSpec.setFilteredNotifications(filteredNotificationSpecMap);

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(4, incidentNotificationMessageAddressPairs.size());
        assertEquals("3_filtered@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
        assertEquals("1_filtered@email.com", incidentNotificationMessageAddressPairs.get(1).getNotificationAddress());
        assertEquals("2_filtered@email.com", incidentNotificationMessageAddressPairs.get(2).getNotificationAddress());
        assertEquals("default@email.com", incidentNotificationMessageAddressPairs.get(3).getNotificationAddress());
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
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(2);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec thirdFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(3);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("3_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);
        filteredNotificationSpecMap.put("third_filtered_notification", thirdFilteredNotificationSpec);

        notificationSpec.setFilteredNotifications(filteredNotificationSpecMap);

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(2, incidentNotificationMessageAddressPairs.size());
        assertEquals("1_filtered@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
        assertEquals("2_filtered@email.com", incidentNotificationMessageAddressPairs.get(1).getNotificationAddress());
    }

    @Test
    void filterNotifications_processAdditionalFiltersSetOnLowerPriority_returnsObjectWithTheHighestPriorityOnly() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(2);
            setProcessAdditionalFilters(true);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);

        notificationSpec.setFilteredNotifications(filteredNotificationSpecMap);

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(1, incidentNotificationMessageAddressPairs.size());
        assertEquals("1_filtered@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
    }

    @Test
    void filterNotifications_commaSeparatedAddresses_returnedBoth() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com, 2_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);

        notificationSpec.setFilteredNotifications(filteredNotificationSpecMap);

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(2, incidentNotificationMessageAddressPairs.size());
        assertEquals("1_filtered@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
        assertEquals("2_filtered@email.com", incidentNotificationMessageAddressPairs.get(1).getNotificationAddress());
    }

    @Test
    void filterNotifications_twoIncidentsWithDescriptions_messageObjectsAreDistinguishableAmongAddressPairs() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setProcessAdditionalFilters(true);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
            setMessage("description 1");
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(2);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
            setMessage("description 2");
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);

        notificationSpec.setFilteredNotifications(filteredNotificationSpecMap);

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, new IncidentNotificationConfigurations(notificationSpec, new IncidentNotificationSpec()));

        assertEquals(2, incidentNotificationMessageAddressPairs.size());
        assertNotEquals(
                incidentNotificationMessageAddressPairs.get(0).getIncidentNotificationMessage(),
                incidentNotificationMessageAddressPairs.get(1).getIncidentNotificationMessage()
        );
        assertEquals("1_filtered@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
        assertEquals("description 1", incidentNotificationMessageAddressPairs.get(0).getIncidentNotificationMessage().getMessage());
        assertEquals("2_filtered@email.com", incidentNotificationMessageAddressPairs.get(1).getNotificationAddress());
        assertEquals("description 2", incidentNotificationMessageAddressPairs.get(1).getIncidentNotificationMessage().getMessage());
    }

    private IncidentNotificationSpec createIncidentNotificationSpecForMixedCase(){
        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        notificationSpec.setIncidentOpenedAddresses("default@email.com");

        FilteredNotificationSpec filteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setProcessAdditionalFilters(true);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("1_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
            setMessage("description 1");
        }};

        FilteredNotificationSpec secondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(2);
            setProcessAdditionalFilters(true);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("2_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
            setMessage("description 2");
        }};

        FilteredNotificationSpecMap filteredNotificationSpecMap = new FilteredNotificationSpecMap();
        filteredNotificationSpecMap.put("one_filtered_notification", filteredNotificationSpec);
        filteredNotificationSpecMap.put("second_filtered_notification", secondFilteredNotificationSpec);
        notificationSpec.setFilteredNotifications(filteredNotificationSpecMap);

        return notificationSpec;
    }

    private IncidentNotificationSpec createGlobalIncidentNotificationSpecForMixedCase(){
        IncidentNotificationSpec globalNotificationSpec = new IncidentNotificationSpec();
        globalNotificationSpec.setIncidentOpenedAddresses("default_global@email.com");

        FilteredNotificationSpec globalFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(1);
            setProcessAdditionalFilters(true);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("1_global_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
            setMessage("description global 1");
        }};

        FilteredNotificationSpec globalSecondFilteredNotificationSpec = new FilteredNotificationSpec(){{
            setPriority(2);
            setProcessAdditionalFilters(true);
            setTarget(new IncidentNotificationTargetSpec(){{
                setIncidentOpenedAddresses("2_global_filtered@email.com");
            }});
            setFilter(new NotificationFilterSpec(){{
                setConnection("connection_name");
            }});
            setMessage("description global 2");
        }};

        FilteredNotificationSpecMap globalFilteredNotificationSpecMap = new FilteredNotificationSpecMap();
        globalFilteredNotificationSpecMap.put("first_global_filtered_notification", globalFilteredNotificationSpec);
        globalFilteredNotificationSpecMap.put("second_global_filtered_notification", globalSecondFilteredNotificationSpec);
        globalNotificationSpec.setFilteredNotifications(globalFilteredNotificationSpecMap);

        return globalNotificationSpec;
    }

    @Test
    void filterNotifications_globalAndConnectionNotificationsMixed_FirstConnectionByPriorityThenGlobalByPriority() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);

        IncidentNotificationSpec notificationSpec = createIncidentNotificationSpecForMixedCase();
        IncidentNotificationSpec globalNotificationSpec = createGlobalIncidentNotificationSpecForMixedCase();

        IncidentNotificationConfigurations incidentNotificationConfigurations = new IncidentNotificationConfigurations(
                notificationSpec,
                globalNotificationSpec
        );

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, incidentNotificationConfigurations);

        assertEquals(5, incidentNotificationMessageAddressPairs.size());
        assertEquals("1_filtered@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
        assertEquals("description 1", incidentNotificationMessageAddressPairs.get(0).getIncidentNotificationMessage().getMessage());

        assertEquals("2_filtered@email.com", incidentNotificationMessageAddressPairs.get(1).getNotificationAddress());
        assertEquals("description 2", incidentNotificationMessageAddressPairs.get(1).getIncidentNotificationMessage().getMessage());

        assertEquals("1_global_filtered@email.com", incidentNotificationMessageAddressPairs.get(2).getNotificationAddress());
        assertEquals("description global 1", incidentNotificationMessageAddressPairs.get(2).getIncidentNotificationMessage().getMessage());

        assertEquals("2_global_filtered@email.com", incidentNotificationMessageAddressPairs.get(3).getNotificationAddress());
        assertEquals("description global 2", incidentNotificationMessageAddressPairs.get(3).getIncidentNotificationMessage().getMessage());

        assertEquals("default@email.com", incidentNotificationMessageAddressPairs.get(4).getNotificationAddress());
        assertEquals(null, incidentNotificationMessageAddressPairs.get(4).getIncidentNotificationMessage().getMessage());
    }

    @Test
    void filterNotifications_globalAndConnectionNotificationsButDefaultIsSetGlobalOnly_theLastAddressIsGlobal() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);

        IncidentNotificationSpec notificationSpec = createIncidentNotificationSpecForMixedCase();
        notificationSpec.setIncidentOpenedAddresses(null);
        IncidentNotificationSpec globalNotificationSpec = createGlobalIncidentNotificationSpecForMixedCase();

        IncidentNotificationConfigurations incidentNotificationConfigurations = new IncidentNotificationConfigurations(
                notificationSpec,
                globalNotificationSpec
        );

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, incidentNotificationConfigurations);

        assertEquals(5, incidentNotificationMessageAddressPairs.size());

        assertEquals("default_global@email.com", incidentNotificationMessageAddressPairs.get(4).getNotificationAddress());
        assertEquals(null, incidentNotificationMessageAddressPairs.get(4).getIncidentNotificationMessage().getMessage());
    }

    @Test
    void filterNotifications_onlyGlobalDefaultItSet_setOnlyGlobalAddress() {
        Instant instant = LocalDateTime.of(2023, 9, 1, 12, 30, 20).toInstant(ZoneOffset.UTC);
        IncidentNotificationMessage message = SampleIncidentMessages.createSampleIncidentMessage(instant, IncidentStatus.open);

        IncidentNotificationSpec notificationSpec = new IncidentNotificationSpec();
        IncidentNotificationSpec globalNotificationSpec = new IncidentNotificationSpec();
        globalNotificationSpec.setIncidentOpenedAddresses("default_global@email.com");

        IncidentNotificationConfigurations incidentNotificationConfigurations = new IncidentNotificationConfigurations(
                notificationSpec,
                globalNotificationSpec
        );

        List<IncidentNotificationMessageAddressPair> incidentNotificationMessageAddressPairs = sut.filterNotifications(message, incidentNotificationConfigurations);

        assertEquals(1, incidentNotificationMessageAddressPairs.size());
        assertEquals("default_global@email.com", incidentNotificationMessageAddressPairs.get(0).getNotificationAddress());
    }

}