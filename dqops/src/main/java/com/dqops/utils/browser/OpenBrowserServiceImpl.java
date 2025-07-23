/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.browser;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Opens an url in a browser.
 */
@Component
public class OpenBrowserServiceImpl implements OpenBrowserService {
    /**
     * Opens an url in a browser.
     * @param url Url to be opened.
     * @throws OpenBrowserFailedException When it was not possible to open a browser. The error message should be shown to the user.
     */
    public void openUrlInBrowser(String url) {
        // thanks Dave: https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java

        String myOS = System.getProperty("os.name").toLowerCase();

        try {
            if(Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(url));
            } else { // we have to find out which OS it is
                Runtime runtime = Runtime.getRuntime();
                if (myOS.contains("windows")) {
                    runtime.exec(new String[] {"cmd", "/k", "start", url});
                } else if (myOS.contains("mac")) { // Apples
                    runtime.exec("open " + url);
                } else if (myOS.contains("nix") || myOS.contains("nux")) { // Linux flavours
                    runtime.exec("xdg-open " + url);
                } else {
                    throw new OpenBrowserFailedException("Your operating system is not supported yet, please open a browser and navigate to this url: " + url);
                }
            }
        }
        catch (OpenBrowserFailedException ex) {
            throw ex;
        }
        catch(IOException | URISyntaxException ex) {
            throw new OpenBrowserFailedException("Cannot open url: " + url + ", reason: " + ex.getMessage(), ex);
        }
    }
}
