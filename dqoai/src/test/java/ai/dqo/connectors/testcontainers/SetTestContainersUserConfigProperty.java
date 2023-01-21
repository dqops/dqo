package ai.dqo.connectors.testcontainers;

import org.testcontainers.utility.TestcontainersConfiguration;

/**
 * Command line operation that modifies the ~/.testcontainers.properties file and changes values of properties.
 * It is used to enable or disable teh reusable containers.
 */
public class SetTestContainersUserConfigProperty {
    /**
     * Main method of an operation that changes a parameter value in the ~/.testcontainers.properties file.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid command line parameters");
            System.out.println("Accepted syntax:");

            String myOS = System.getProperty("os.name").toLowerCase();
            if (myOS.contains("windows")) {
                System.out.println("set_testcontainers_property.cmd <property_name> <property_value>");
            }
            else {
                System.out.println("./set_testcontainers_property.sh <property_name> <property_value>");
            }

            System.exit(-1);
        }

        TestcontainersConfiguration configuration = TestcontainersConfiguration.getInstance();
        // set a value
        configuration.updateUserConfig(args[0], args[1]);
    }
}
