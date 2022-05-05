/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.browser;

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
