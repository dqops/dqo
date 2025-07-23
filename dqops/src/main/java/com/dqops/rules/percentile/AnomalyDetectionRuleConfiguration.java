/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rules.percentile;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Constants with additional configuration of parameters that are passed to anomaly detection checks that use the t-student distribution.
 */
public final class AnomalyDetectionRuleConfiguration {
    /**
     * The number of degrees of freedom for the t-student distribution used to detect anomalies using the Z-score algorithm in the free version.
     */
    public static final String DEGREES_OF_FREEDOM_PARAMETER = "degrees_of_freedom";

    /**
     * The number of degrees of freedom for the t-student distribution used to detect anomalies using a machine learning algorithm in the paid version of DQOps.
     */
    public static final String AI_DEGREES_OF_FREEDOM_PARAMETER = "ai_degrees_of_freedom";
    public static final String ANDERSON_SIGNIFICANCE_LEVEL_PARAMETER = "anderson_significance_level";
    public static final String KOLMOGOROV_SIGNIFICANCE_LEVEL_PARAMETER = "kolmogorov_significance_level";

    /**
     * The additional rule parameters passed to the rules that use t-student distribution.
     * We are using our "Degree of freedom" to be prepared for bigger anomalies.
     */
    public static Map<String, String> T_STUDENT_DISTRIBUTION_PARAMETERS = Collections.unmodifiableMap(new LinkedHashMap<>() {{
        put(DEGREES_OF_FREEDOM_PARAMETER, "5");
        put(AI_DEGREES_OF_FREEDOM_PARAMETER, "8");
//        put(ANDERSON_SIGNIFICANCE_LEVEL_PARAMETER, "1.0");   // levels supported by the Anderson-Darling test: [15. , 10. ,  5. ,  2.5,  1. ]
//        put(KOLMOGOROV_SIGNIFICANCE_LEVEL_PARAMETER, "1.0");
    }});
}
