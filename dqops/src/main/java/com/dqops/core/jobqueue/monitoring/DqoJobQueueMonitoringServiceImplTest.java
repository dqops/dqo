/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.monitoring;

import com.dqops.BaseTest;
import com.dqops.core.jobqueue.DqoQueueJobId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TreeMap;

@SpringBootTest
public class DqoJobQueueMonitoringServiceImplTest extends BaseTest {
    private static DqoJobHistoryEntryModel createJobHistoryEntry(DqoJobStatus status, Instant statusChangedAt) {
        DqoJobHistoryEntryModel jobHistoryEntryModel = new DqoJobHistoryEntryModel();
        jobHistoryEntryModel.setStatus(status);
        jobHistoryEntryModel.setStatusChangedAt(statusChangedAt);
        return jobHistoryEntryModel;
    }

    @Test
    void findFinishedJobsOlderThan_whenRecentlyFinishedJobHasLowerIdThanOldJobs_thenReturnsAllOldFinishedJobs() {
        Instant now = Instant.now();
        Instant threshold = now.minus(1, ChronoUnit.HOURS);
        TreeMap<DqoQueueJobId, DqoJobHistoryEntryModel> allJobs = new TreeMap<>();
        allJobs.put(new DqoQueueJobId(1L), createJobHistoryEntry(DqoJobStatus.finished, now)); // long-running job, finished a moment ago
        allJobs.put(new DqoQueueJobId(2L), createJobHistoryEntry(DqoJobStatus.finished, now.minus(2, ChronoUnit.HOURS)));
        allJobs.put(new DqoQueueJobId(3L), createJobHistoryEntry(DqoJobStatus.failed, now.minus(3, ChronoUnit.HOURS)));
        allJobs.put(new DqoQueueJobId(4L), createJobHistoryEntry(DqoJobStatus.running, now.minus(3, ChronoUnit.HOURS)));
        allJobs.put(new DqoQueueJobId(5L), createJobHistoryEntry(DqoJobStatus.cancelled, now.minus(90, ChronoUnit.MINUTES)));
        allJobs.put(new DqoQueueJobId(6L), createJobHistoryEntry(DqoJobStatus.finished, now.minus(1, ChronoUnit.MINUTES)));

        List<DqoQueueJobId> oldJobIds = DqoJobQueueMonitoringServiceImpl.findFinishedJobsOlderThan(allJobs, threshold);

        Assertions.assertEquals(3, oldJobIds.size());
        Assertions.assertEquals(2L, oldJobIds.get(0).getJobId());
        Assertions.assertEquals(3L, oldJobIds.get(1).getJobId());
        Assertions.assertEquals(5L, oldJobIds.get(2).getJobId());
    }

    @Test
    void findFinishedJobsOlderThan_whenNoJobIsOldEnough_thenReturnsEmptyList() {
        Instant now = Instant.now();
        TreeMap<DqoQueueJobId, DqoJobHistoryEntryModel> allJobs = new TreeMap<>();
        allJobs.put(new DqoQueueJobId(1L), createJobHistoryEntry(DqoJobStatus.finished, now));
        allJobs.put(new DqoQueueJobId(2L), createJobHistoryEntry(DqoJobStatus.running, now.minus(3, ChronoUnit.HOURS)));

        List<DqoQueueJobId> oldJobIds = DqoJobQueueMonitoringServiceImpl.findFinishedJobsOlderThan(allJobs, now.minus(1, ChronoUnit.HOURS));

        Assertions.assertTrue(oldJobIds.isEmpty());
    }
}