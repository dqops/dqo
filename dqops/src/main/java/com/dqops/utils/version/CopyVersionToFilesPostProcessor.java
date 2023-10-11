/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.utils.version;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Special java class called from the build script. Replaces all file lines with version numbers.
 * The version number is taken from the "VERSION" file in the repository root.
 * In order to update the version, just change the value in the VERSION file and run the 'apply-version' maven profile.
 *
 * NOTES: In order to ensure that the version numbers are increasing and the PyPi package versions are numeric only,
 * the "alpha" and "beta" texts in the version number are removed in the python package version. Also all "-" and "_" characters
 * are replaced with a '.' dot in the python package name. So a version 0.2.0-beta2 becomes 0.2.0.2.
 * When a final version (after a beta) is released, the version number should be upgraded to 0.2.0.3 (to be the next available version).
 */
public class CopyVersionToFilesPostProcessor {
    /**
     * Main method of the build operation that copies the version number from the VERSION file and applies it to all files.
     * @param args Command line arguments.
     * @throws Exception When something fails.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("DQOps version configure utility");
            System.out.println("Missing required parameter: <project root path>");
            return;
        }

        Path repositoryRootPath = Path.of(args[0]).resolve("..").toAbsolutePath().normalize();
        Path pathToVersionFile = repositoryRootPath.resolve("VERSION");
        String version = Files.readString(pathToVersionFile, StandardCharsets.UTF_8);
        String pyPiPackageVersion = version
                .replace('-', '.').replace('_', '.')
                .replace("alpha", "").replace("beta", "");

        updateVersionInPomXml(repositoryRootPath.resolve("pom.xml"), version);
        updateVersionInPomXml(repositoryRootPath.resolve("lib/pom.xml"), version);
        updateVersionInPomXml(repositoryRootPath.resolve("dqops/pom.xml"), version);
        updateVersionInPomXml(repositoryRootPath.resolve("distribution/pom.xml"), version);

        updateVersionInPackageJson(repositoryRootPath.resolve("dqops/src/main/frontend/package.json"), version);

        updateVersionInPythonVersionFile(repositoryRootPath.resolve("distribution/python/dqops/version.py"),
                version, pyPiPackageVersion);

        updateVersionInWindowsDqoCmd(repositoryRootPath.resolve("dqo.cmd"), version);
        updateVersionInLinuxDqoScript(repositoryRootPath.resolve("dqo"), version);

        updateVersionInBannerTxt(repositoryRootPath.resolve("dqops/src/main/resources/banner.txt"), version);
        updateVersionInIntelliJRunConfig(repositoryRootPath.resolve(".run/dqo run.run.xml"), version);
    }

    /**
     * Replace the version in pom.xml file.
     * @param pathToPomXml Path to a pom.xml file.
     * @param version New version number.
     * @throws Exception
     */
    public static void updateVersionInPomXml(Path pathToPomXml, String version) throws Exception {
        final String markerComment = "<!-- DQOps Version, do not touch (changed automatically) -->";

        List<String> lines = Files.readAllLines(pathToPomXml, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(markerComment)) {
                String correctLine = "    <version>" + version + "</version>  " + markerComment;
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    Files.write(pathToPomXml, lines, StandardCharsets.UTF_8);
                }

                return;
            }
        }
    }

    /**
     * Replace the version number in the package.json file.
     * @param pathToPackageJson Path to a package.json
     * @param version New version number.
     * @throws Exception
     */
    public static void updateVersionInPackageJson(Path pathToPackageJson, String version) throws Exception {
        List<String> lines = Files.readAllLines(pathToPackageJson, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("\"version\":")) {
                String correctLine = "  \"version\": \"" + version + "\",";
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    Files.write(pathToPackageJson, lines, StandardCharsets.UTF_8);
                }

                return;
            }
        }
    }

    /**
     * Replace the version number in the dqo.cmd script
     * @param pathToDqoScript Path to a dqo.cmd
     * @param version New version number.
     * @throws Exception
     */
    public static void updateVersionInWindowsDqoCmd(Path pathToDqoScript, String version) throws Exception {
        List<String> lines = Files.readAllLines(pathToDqoScript, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("set DQO_VERSION=")) {
                String correctLine = "set DQO_VERSION=" + version;
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    Files.write(pathToDqoScript, lines, StandardCharsets.UTF_8);
                }

                return;
            }
        }
    }

    /**
     * Replace the version number in the dqo shell script
     * @param pathToDqoScript Path to a dqo shell
     * @param version New version number.
     * @throws Exception
     */
    public static void updateVersionInLinuxDqoScript(Path pathToDqoScript, String version) throws Exception {
        List<String> lines = Files.readAllLines(pathToDqoScript, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("export DQO_VERSION=")) {
                String correctLine = "export DQO_VERSION=" + version;
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    byte[] newFileContent = String.join("\n", lines).getBytes(StandardCharsets.UTF_8);
                    Files.write(pathToDqoScript, newFileContent);
                }

                return;
            }
        }
    }

    /**
     * Replace the version number in the banner.txt file.
     * @param pathToBannerTxt Path to a banner.txt
     * @param version New version number.
     * @throws Exception
     */
    public static void updateVersionInBannerTxt(Path pathToBannerTxt, String version) throws Exception {
        List<String> lines = Files.readAllLines(pathToBannerTxt, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String bannerPrefix = " :: DQOps Data Quality Operations Center ::    (v";
            if (line.startsWith(bannerPrefix)) {
                String correctLine = bannerPrefix + version + ")";
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    Files.write(pathToBannerTxt, lines, StandardCharsets.UTF_8);
                }

                return;
            }
        }
    }

    /**
     * Replace the version number in the IntelliJ run config.
     * @param pathToRunConfig Path to an IntelliJ run config.
     * @param version New version number.
     * @throws Exception
     */
    public static void updateVersionInIntelliJRunConfig(Path pathToRunConfig, String version) throws Exception {
        List<String> lines = Files.readAllLines(pathToRunConfig, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String jarReferenceLinePrefix = "    <option name=\"JAR_PATH\" value=\"$PROJECT_DIR$/dqops/target/dqo-dqops-";
            if (line.startsWith(jarReferenceLinePrefix)) {
                String correctLine = jarReferenceLinePrefix + version + ".jar\" />";
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    Files.write(pathToRunConfig, lines, StandardCharsets.UTF_8);
                }

                return;
            }
        }
    }

    /**
     * Replace the version number in the version.py file.
     * @param pathToVersionPy Path to a version.py file.
     * @param applicationVersion New version number for the application (including alpha and beta markers).
     * @param pypiPackageVersion PyPi numeric only version number.
     * @throws Exception
     */
    public static void updateVersionInPythonVersionFile(Path pathToVersionPy,
                                                        String applicationVersion,
                                                        String pypiPackageVersion) throws Exception {
        List<String> lines = Files.readAllLines(pathToVersionPy, StandardCharsets.UTF_8);
        boolean modified = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.startsWith("VERSION = ")) {
                String correctLine = "VERSION = \"" + applicationVersion + "\"";
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    modified = true;
                }
            }

            if (line.startsWith("PIP_VERSION = ")) {
                String correctLine = "PIP_VERSION = \"" + pypiPackageVersion + "\"";
                if (!Objects.equals(line, correctLine)) {
                    lines.set(i, correctLine);
                    modified = true;
                }
            }
        }

        if (modified) {
            Files.write(pathToVersionPy, lines, StandardCharsets.UTF_8);
        }
    }
}
