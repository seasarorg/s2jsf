/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.jsf.html;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

/**
 * @author manhole
 * @author shot
 * @author yone
 */
// TODO handle "javascript: xxxx" attribute (really necessary?)
public class HtmlResponseWriter extends ResponseWriter {

    private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

    private static List EMPTY_ELEMENTS = Arrays
            .asList(new String[] { "area", "br", "base", "col", "hr", "img",
                    "input", "link", "meta", "param" });

    private static final char[] reserved = { ';', '/', '?', ':', '@', '&', '=',
            '+', '$', ',' };

    private static final char[] unescape;

    static {
        unescape = new char[reserved.length + 1];
        System.arraycopy(reserved, 0, unescape, 0, reserved.length);
        unescape[unescape.length - 1] = '#';
    }

    private Writer writer;

    private String contentType;

    private String characterEncoding;

    private boolean startTagOpening;

    private boolean shouldEscape = true;

    public void startElement(String name, UIComponent componentForElement)
            throws IOException {
        assertNotNull("name", name);
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write("<");
        writer.write(name);
        if ("script".equalsIgnoreCase(name)) {
            shouldEscape = false;
        } else {
            shouldEscape = true;
        }

        startTagOpening = true;
    }

    protected void closeStartTagIfOpening(Writer writer) throws IOException {
        if (startTagOpening) {
            writer.write(">");
            startTagOpening = false;
        }
    }

    public void endElement(String name) throws IOException {
        assertNotNull("name", name);
        Writer writer = getWriter();
        if (startTagOpening) {
            if (isEmptyElement(name)) {
                writer.write(" />");
            } else {
                writer.write(">");
                writer.write("</" + name + ">");
            }
            startTagOpening = false;
        } else {
            writer.write("</" + name + ">");
        }
        shouldEscape = true;
    }

    protected boolean isEmptyElement(String name) {
        return EMPTY_ELEMENTS.contains(name);
    }

    public void writeAttribute(String name, Object value, String property)
            throws IOException {
        assertNotNull("name", name);
        if (!startTagOpening) {
            throw new IllegalStateException(
                    "there is no currently open element");
        }
        String strValue = (value == null) ? "" : value.toString();
        Writer writer = getWriter();
        writer.write(" ");
        writer.write(name);
        writer.write("=\"");
        writer.write(escapeAttribute(strValue));
        writer.write("\"");
    }

    public void writeURIAttribute(String name, Object value, String property)
            throws IOException {
        assertNotNull("name", name);
        if (!startTagOpening) {
            throw new IllegalStateException(
                    "there is no currently open element");
        }
        Writer writer = getWriter();
        String strValue = encodeURIAttribute(value.toString());

        writer.write(" ");
        writer.write(name);
        writer.write("=\"");
        writer.write(strValue);
        writer.write("\"");
    }

    public void writeComment(Object comment) throws IOException {
        assertNotNull("comment", comment);
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write("<!--");
        writer.write(comment.toString());
        writer.write("-->");
    }

    public void writeText(Object text, String property) throws IOException {
        assertNotNull("text", text);
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        String str = null;
        if (shouldEscape) {
            str = htmlSpecialChars(text.toString());
        } else {
            str = text.toString();
        }
        writer.write(str);
    }

    public void writeText(char text[], int off, int len) throws IOException {
        assertNotNull("text", text);
        writeText(new String(text, off, len), null);
    }

    protected String htmlSpecialChars(final String s) {
        return htmlSpecialChars(s, true, true);
    }

    private String htmlSpecialChars(final String s, final boolean quote,
            final boolean amp) {
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            // &nbsp; 0xA0
            if ((int) c == '\u00A0') {
                sb.append("&nbsp;");
            } else if (c == '<') {
                sb.append("&lt;");
            } else if (c == '>') {
                sb.append("&gt;");
            } else if (amp && c == '&') {
                sb.append("&amp;");
            } else if (c == '"') {
                sb.append("&quot;");
            } else if (quote && c == '\'') {
                sb.append("&#39;");
            } else {
                sb.append(c);
            }
        }
        return new String(sb);
    }

    protected String escapeAttribute(String s) {
        return htmlSpecialChars(s, false, false);
    }

    public ResponseWriter cloneWithWriter(Writer writer) {
        assertNotNull("writer", writer);
        HtmlResponseWriter clone = new HtmlResponseWriter();
        clone.setWriter(writer);
        clone.setContentType(getContentType());
        clone.setCharacterEncoding(getCharacterEncoding());
        return clone;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(cbuf, off, len);
    }

    public void write(char[] cbuf) throws IOException {
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(cbuf);
    }

    public void write(int c) throws IOException {
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(c);
    }

    public void write(String str) throws IOException {
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(str);
    }

    public void write(String str, int off, int len) throws IOException {
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.write(str, off, len);
    }

    public void flush() throws IOException {
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
    }

    public void close() throws IOException {
        Writer writer = getWriter();
        closeStartTagIfOpening(writer);
        writer.close();
    }

    public void startDocument() throws IOException {
    }

    public void endDocument() throws IOException {
        closeStartTagIfOpening(getWriter());
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharacterEncoding() {
        if (characterEncoding == null) {
            return DEFAULT_CHARACTER_ENCODING;
        }
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    protected String encodeURIAttribute(String url) throws IOException {
        char[] chars = url.toCharArray();
        final StringBuffer sb = new StringBuffer(url.length() + 100);
        final int length = chars.length;
        forLoop: for (int i = 0; i < length; i++) {
            final char c = chars[i];
            if (contains(unescape, c)) {
                sb.append(c);
                if ('?' == c) {
                    if (i < length) {
                        sb.append(encodeQueryString(url.substring(i + 1)));
                    }
                    break forLoop;
                }
            } else {
                sb.append(URLEncoder.encode(String.valueOf(c),
                        getCharacterEncoding()));
            }
        }
        return new String(sb);
    }

    public String encodeQueryString(String s) throws IOException {
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        final String encoding = getCharacterEncoding();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
            case ' ': // 32
                sb.append("%20");
                break;
            case '!': // 33
                sb.append("!");
                break;
            case '#': // 35
                sb.append("#");
                break;
            case '%': // 37
                sb.append("%");
                break;
            case '&': // 38
                // sb.append("&");
                sb.append("&amp;");
                break;
            case '\'': // 39
                sb.append("'");
                break;
            case '(': // 40
                sb.append("(");
                break;
            case ')': // 41
                sb.append(")");
                break;
            case '+': // 43
                sb.append("+");
                break;
            case '/': // 47
                sb.append("/");
                break;
            case '=': // 61
                sb.append("=");
                break;
            case '~': // 126
                sb.append("~");
                break;
            default:
                sb.append(URLEncoder.encode(String.valueOf(c), encoding));
                break;
            }
        }
        return new String(sb);
    }

    public String toString() {
        return writer.toString();
    }

    // TODO delete after using S2.4
    private static void assertNotNull(String message, Object target) {
        if (target == null) {
            throw new NullPointerException(message);
        }
    }

    private static boolean contains(char[] array, char ch) {
        return -1 < indexOf(array, ch);
    }

    private static int indexOf(char[] array, char ch) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                char c = array[i];
                if (ch == c) {
                    return i;
                }
            }
        }
        return -1;
    }

}
