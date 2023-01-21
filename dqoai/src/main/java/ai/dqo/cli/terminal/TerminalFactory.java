package ai.dqo.cli.terminal;

/**
 * Provides instances of the {@link TerminalReader} and {@link TerminalWriter} that are created when needed.
 */
public interface TerminalFactory {
    /**
     * Returns the instance of the terminal reader.
     *
     * @return Terminal reader.
     */
    TerminalReader getReader();

    /**
     * Returns the instance of the terminal writer.
     *
     * @return Terminal writer.
     */
    TerminalWriter getWriter();
}
