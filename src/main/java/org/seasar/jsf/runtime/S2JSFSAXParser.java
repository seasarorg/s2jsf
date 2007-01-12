/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.cyberneko.html.HTMLConfiguration;

/**
 * @author shot
 * @author yone
 */
public class S2JSFSAXParser extends AbstractSAXParser {

    public S2JSFSAXParser(HTMLConfiguration config) {
        super(config);
    }

    public void xmlDecl(String version, String encoding, String standalone,
            Augmentations augs) throws XNIException {
        super.xmlDecl(version, encoding, standalone, augs);
        StringBuffer buf = new StringBuffer();
        if (version != null) {
            buf.append("<?xml version=\"");
            buf.append(version);
            buf.append("\"");
        }
        if (encoding != null) {
            buf.append(" encoding=\"");
            buf.append(encoding);
            buf.append("\"");
        }
        if (standalone != null) {
            buf.append(" standalone=\"");
            buf.append(standalone);
            buf.append("\"");
        }

        buf.append("?>\n");
        String xml = buf.toString();

        super.characters(new XMLString(xml.toCharArray(), 0, xml.length()),
                augs);
    }
    
}
