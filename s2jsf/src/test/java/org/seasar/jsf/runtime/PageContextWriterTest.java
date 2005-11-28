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
        assertEquals("writeÇÕPageContextImpl#getOut()Ç÷àœè˜ÇµÇƒÇ¢ÇÈÇ±Ç∆", "7650", sb
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
        assertEquals("flushÇÕPageContextImpl#getOut()Ç÷àœè˜ÇµÇƒÇ¢ÇÈÇ±Ç∆", true, called[0]);
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
        assertEquals("closeÇÕPageContextImpl#getOut()Ç÷àœè˜ÇµÇƒÇ¢ÇÈÇ±Ç∆", true, called[0]);
    }

}
