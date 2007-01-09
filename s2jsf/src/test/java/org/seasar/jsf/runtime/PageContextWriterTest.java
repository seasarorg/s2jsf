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

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import junit.framework.TestCase;

import org.seasar.jsf.jsp.JspWriterImpl;
import org.seasar.jsf.jsp.PageContextImpl;

public class PageContextWriterTest extends TestCase {

    public void testWrite() throws Exception {
        // ## Arrange ##
        final StringBuffer sb = new StringBuffer();
        PageContextWriter pageContextWriter = new PageContextWriter(
            new PageContextImpl() {
                public JspWriter getOut() {
                    return new JspWriterImpl() {
                        public void write(char[] cbuf, int off, int len)
                            throws IOException {
                            sb.append(cbuf, off, len);
                        }
                    };
                }
            });

        // ## Act ##
        pageContextWriter.write("7650");

        // ## Assert ##
        assertEquals("writeはPageContextImpl#getOut()へ委譲していること", "7650", sb
            .toString());
    }

    public void testFlush() throws Exception {
        // ## Arrange ##
        final boolean[] called = { false };
        PageContextWriter pageContextWriter = new PageContextWriter(
            new PageContextImpl() {
                public JspWriter getOut() {
                    return new JspWriterImpl() {
                        public void flush() throws IOException {
                            called[0] = true;
                        }
                    };
                }
            });
        // ## Act ##
        pageContextWriter.flush();

        // ## Assert ##
        assertEquals("flushはPageContextImpl#getOut()へ委譲していること", true, called[0]);
    }

    public void testClose() throws Exception {
        // ## Arrange ##
        final boolean[] called = { false };
        PageContextWriter pageContextWriter = new PageContextWriter(
            new PageContextImpl() {
                public JspWriter getOut() {
                    return new JspWriterImpl() {
                        public void close() throws IOException {
                            called[0] = true;
                        };
                    };
                }
            });
        // ## Act ##
        pageContextWriter.close();

        // ## Assert ##
        assertEquals("closeはPageContextImpl#getOut()へ委譲していること", true, called[0]);
    }

}
