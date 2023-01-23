package ai.dqo.cli.terminal;

import ai.dqo.utils.BeanFactoryObjectMother;

/**
 * Create a terminal factory instance.
 */
public class TerminalFactoryObjectMother {
    /**
     * Returns the default terminal factory that returns a real terminal reader and writer.
     * @return Default terminal factory.
     */
    public static TerminalFactory getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(TerminalFactory.class);
    }
}
