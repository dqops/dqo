package com.dqops.core.dqocloud.login;

import com.dqops.utils.BeanFactoryObjectMother;

public class InstanceCloudLoginServiceObjectMother {

    public static InstanceCloudLoginServiceImpl getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(InstanceCloudLoginServiceImpl.class);
    }

}
