/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.jsf.runtime;

import java.io.IOException;

import org.apache.xerces.util.XMLAttributesImpl;
import org.cyberneko.html.HTMLConfiguration;
import org.cyberneko.html.HTMLScanner;

/**
 * @author shot
 */
public class S2JSFConfiguration extends HTMLConfiguration {

    public S2JSFConfiguration() {
        fDocumentScanner = new S2JSFHtmlScanner();
        addComponent(fDocumentScanner);
    }

    public static class S2JSFHtmlScanner extends HTMLScanner {

        public S2JSFHtmlScanner() {
            fContentScanner = new ContentScanner() {

                protected boolean scanAttribute(XMLAttributesImpl attributes,
                        boolean[] empty, char endc) throws IOException {
                    boolean b = super.scanAttribute(attributes, empty, endc);
                    for (int i = 0; i < attributes.getLength(); i++) {
                        String nonNormalizedValue = attributes
                                .getNonNormalizedValue(i);
                        attributes.setValue(i, nonNormalizedValue);
                    }
                    return b;
                }

            };
        }
    }
}
