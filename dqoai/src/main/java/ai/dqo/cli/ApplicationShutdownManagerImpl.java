package ai.dqo.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring boot shutdown manager - allows the application to request the shutdown, because the web server must be stopped.
 */
@Component
public class ApplicationShutdownManagerImpl implements ApplicationShutdownManager {
    private ApplicationContext applicationContext;

    @Autowired
    public ApplicationShutdownManagerImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Initializes an application shutdown, given the application return code to return.
     * @param returnCode Return code.
     */
    @Override
    public void initiateShutdown(int returnCode){
        SpringApplication.exit(this.applicationContext, () -> returnCode);
    }
}
