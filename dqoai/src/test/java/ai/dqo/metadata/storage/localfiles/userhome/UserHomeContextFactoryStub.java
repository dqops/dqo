package ai.dqo.metadata.storage.localfiles.userhome;

/**
 * Testable stub that returns a provided user home context.
 */
public class UserHomeContextFactoryStub implements UserHomeContextFactory {
    private UserHomeContext userHomeContext;

    /**
     * Create a user home context factory stub that will always return the same user come context.
     * @param userHomeContext User home context to return.
     */
    public UserHomeContextFactoryStub(UserHomeContext userHomeContext) {
        this.userHomeContext = userHomeContext;
    }

    /**
     * Opens a local home context, loads the files from the local file system.
     *
     * @return User home context with an active user home model that is backed by the local home file system.
     */
    @Override
    public UserHomeContext openLocalUserHome() {
        return this.userHomeContext;
    }
}
