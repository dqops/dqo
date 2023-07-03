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
package com.dqops.cli.commands.settings.impl;

import com.google.api.client.util.Strings;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;

/**
 * Editor finder service.
 */
@Component
public class EditorFinderServiceImpl implements EditorFinderService {

	public String findMacAppsPath() {
		File usersDirectory = new File("/Users");
		if (!usersDirectory.exists()) {
			return null;
		}
		File users[] = usersDirectory.listFiles();
		for (File user : users) {
			String userName = user.getAbsolutePath();
			String appsPath = userName + "Applications";
			File appsDirectory = new File(appsPath);
			if (!appsDirectory.exists()) {
				continue;
			}
			return appsPath;
		}
		return null;
	}

	public String findBinLinuxPath() {
		File usersDirectory = new File("/usr/bin");
		if (!usersDirectory.exists()) {
			return null;
		}
		return "/usr/bin";
	}

	public String findVSC() {
		File usersDirectory = new File("C:\\Users");
		if (!usersDirectory.exists()) {
			return null;
		}
		File users[] = usersDirectory.listFiles();
		for (File user : users) {
			String userName = user.getAbsolutePath();
			String programsPath = userName + "\\AppData\\Local\\Programs";
			File programsDirectory = new File(programsPath);
			if (!programsDirectory.exists()) {
				continue;
			}
			for (File candidate : programsDirectory.listFiles()) {
				if (candidate.getName().toLowerCase().contains("v") && candidate.getName().toLowerCase().contains("s")
						&& candidate.getName().toLowerCase().contains("c")) {
					return candidate.getAbsolutePath();
				}
			}
		}
		String linuxPath = findBinLinuxPath();
		String macPath = findMacAppsPath();
		return macPath != null ? macPath : linuxPath;
	}

	public String findTemplateWithDirName(String jetBrainsDirectoryPath, String template) {
		File jetBrainsDirectory = new File(jetBrainsDirectoryPath);
		String linuxPath = findBinLinuxPath();
		String macPath = findMacAppsPath();
		if (!jetBrainsDirectory.exists()) {
			return macPath != null ? macPath : linuxPath;
		}
		for (File candidate : jetBrainsDirectory.listFiles()) {
			if (candidate.getName().toLowerCase().contains(template)) {
				return candidate.getAbsolutePath();
			}
		}
		return macPath != null ? macPath : linuxPath;
	}

	public String findIntelliJ() {
		String linuxPath = findBinLinuxPath();
		String macPath = findMacAppsPath();		String intellijPath = findTemplateWithDirName("C:\\Program Files (x86)\\JetBrains", "intellij");
		if (!Strings.isNullOrEmpty(intellijPath)) {
			return intellijPath;
		}

		intellijPath = findTemplateWithDirName("C:\\Program Files\\JetBrains", "intellij");
		if (!Strings.isNullOrEmpty(intellijPath)) {
			return intellijPath;
		}

		return macPath != null ? macPath : linuxPath;
	}

	public String findEclipse() {
		String linuxPath = findBinLinuxPath();
		String macPath = findMacAppsPath();		String eclipsePath = findTemplateWithDirName("C:\\Program Files (x86)", "eclipse");
		if (!Strings.isNullOrEmpty(eclipsePath)) {
			return eclipsePath;
		}

		eclipsePath = findTemplateWithDirName("C:\\Program Files", "eclipse");
		if (!Strings.isNullOrEmpty(eclipsePath)) {
			return eclipsePath;
		}

		return macPath != null ? macPath : linuxPath;
	}

	public String findPycharm() {
		String linuxPath = findBinLinuxPath();
		String macPath = findMacAppsPath();		String pycharmPath = findTemplateWithDirName("C:\\Program Files (x86)\\JetBrains", "pycharm");
		if (!Strings.isNullOrEmpty(pycharmPath)) {
			return pycharmPath;
		}

		pycharmPath = findTemplateWithDirName("C:\\Program Files\\JetBrains", "pycharm");
		if (!Strings.isNullOrEmpty(pycharmPath)) {
			return pycharmPath;
		}

		return macPath != null ? macPath : linuxPath;
	}

	public ArrayList<EditorInformation> detectEditors() {
		ArrayList<EditorInformation> editors = new ArrayList<>();

		String vscPath = findVSC();
		EditorInformation vscInformation = new EditorInformation("VSC", vscPath);
		editors.add(vscInformation);

		String intellijPath = findIntelliJ();
		if (!Strings.isNullOrEmpty(intellijPath)) {
			EditorInformation intellijInformation = new EditorInformation("IntelliJ", intellijPath);
			editors.add(intellijInformation);
		}

		String eclipsePath = findEclipse();
		if (!Strings.isNullOrEmpty(eclipsePath)) {
			EditorInformation eclipseInformation = new EditorInformation("Eclipse", eclipsePath);
			editors.add(eclipseInformation);
		}

		String pycharmPath = findPycharm();
		if (!Strings.isNullOrEmpty(pycharmPath)) {
			EditorInformation pycharmInformation = new EditorInformation("PyCharm", pycharmPath);
			editors.add(pycharmInformation);
		}

		return editors;
	}

	public ArrayList<EditorInformation> getAllEditors() {
		ArrayList<EditorInformation> editors = new ArrayList<>();

		EditorInformation vscInformation = new EditorInformation("VSC", "");
		editors.add(vscInformation);

		EditorInformation intellijInformation = new EditorInformation("IntelliJ", "");
		editors.add(intellijInformation);

		EditorInformation eclipseInformation = new EditorInformation("Eclipse", "");
		editors.add(eclipseInformation);

		EditorInformation pycharmInformation = new EditorInformation("PyCharm", "");
		editors.add(pycharmInformation);

		return editors;
	}
}
