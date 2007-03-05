package org.seasar.jsf.runtime;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TagProcessorHandlerTest extends TestCase {

    public void testIsElementNode() {
        TagProcessorHandler handler = new TagProcessorHandler();
        assertTrue(handler.isElementNode("body", new HashMap()));
        assertFalse(handler.isElementNode("hoge", new HashMap()));
        assertTrue(handler.isElementNode("hoge", null));
        Map map = new HashMap();
        map.put("value", "#{aaa.bbb}");
        assertTrue(handler.isElementNode("hoge", map));
    }

}
