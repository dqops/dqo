package ai.dqo.cli;

import org.springframework.boot.ApplicationArguments;
import org.springframework.shell.ShellApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * DQO shell runner that captures the cli commands passed over to the CLI, because DQO does not use the Spring Boot Shell.
 */
@Component
public class DqoShellRunner implements ShellApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // just ignore, we will run the command anyway from CliMainCommandRunner, because we are using Picocli command parser, not the spring shell parser
    }
}
