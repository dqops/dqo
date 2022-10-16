package ai.dqo.metadata.storage.localfiles.userhome;

/**
 * User home context factory object mother that creates a testable user home context factory
 * that uses a predefined user home context (in-memory or file based).
 */
public final class UserHomeContextFactoryObjectMother {
    /**
     * Creates a user home context factory that always returns an in-memory user context.
     * @return Returns an in-memory user context.
     */
    public static UserHomeContextFactory createWithInMemoryContext() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        return new UserHomeContextFactoryStub(inMemoryFileHomeContext);
    }

    /**
     * Creates a user home context factory that creates an empty, file-based user context in a temporary folder.
     * @return Returns a stub factory that returns an empty file based user context.
     */
    public static UserHomeContextFactory createWithEmptyTemporaryContext() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        return new UserHomeContextFactoryStub(inMemoryFileHomeContext);
    }
}
