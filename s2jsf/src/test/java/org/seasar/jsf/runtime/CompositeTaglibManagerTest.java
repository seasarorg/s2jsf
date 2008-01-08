/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import junit.framework.TestCase;

import org.seasar.jsf.TagConfig;
import org.seasar.jsf.TaglibConfig;
import org.seasar.jsf.TaglibManager;
import org.seasar.jsf.exception.TagNotFoundRuntimeException;
import org.seasar.jsf.exception.UriNotFoundRuntimeException;

/**
 * @author manhole
 */
public class CompositeTaglibManagerTest extends TestCase {

    public void testNoTaglibManager() throws Exception {
        // ## Arrange ##
        CompositeTaglibManager taglibManager = new CompositeTaglibManager();

        // ## Act ##
        // ## Assert ##
        assertEquals(false, taglibManager.hasTaglibConfig("a"));
        try {
            taglibManager.getTaglibConfig("a");
            fail();
        } catch (UriNotFoundRuntimeException e) {
        }
    }

    public void testOneTaglibManager() throws Exception {
        // ## Arrange ##
        CompositeTaglibManager taglibManager = new CompositeTaglibManager();
        MockTaglibManager mockTaglibManager = new MockTaglibManager();
        MockTaglibConfig taglibConfig = new MockTaglibConfig();
        taglibConfig.setUri("uri_a");
        mockTaglibManager.setTaglibConfig(taglibConfig);

        taglibManager.addTaglibManager(mockTaglibManager);

        // ## Act ##
        // ## Assert ##
        assertEquals(true, taglibManager.hasTaglibConfig("uri_a"));
        assertEquals(taglibConfig, taglibManager.getTaglibConfig("uri_a"));

        assertEquals(false, taglibManager.hasTaglibConfig("uri_b"));
        try {
            taglibManager.getTaglibConfig("uri_b");
            fail();
        } catch (UriNotFoundRuntimeException e) {
        }
    }

    public void testTwoTaglibManager() throws Exception {
        // ## Arrange ##
        CompositeTaglibManager taglibManager = new CompositeTaglibManager();
        {
            MockTaglibManager mockTaglibManager = new MockTaglibManager();
            MockTaglibConfig taglibConfig = new MockTaglibConfig();
            taglibConfig.setUri("uri_a");
            mockTaglibManager.setTaglibConfig(taglibConfig);
            taglibManager.addTaglibManager(mockTaglibManager);
        }
        {
            MockTaglibManager mockTaglibManager = new MockTaglibManager();
            MockTaglibConfig taglibConfig = new MockTaglibConfig();
            taglibConfig.setUri("uri_b");
            mockTaglibManager.setTaglibConfig(taglibConfig);
            taglibManager.addTaglibManager(mockTaglibManager);
        }

        // ## Act ##
        // ## Assert ##
        assertEquals(true, taglibManager.hasTaglibConfig("uri_a"));
        assertEquals("uri_a", taglibManager.getTaglibConfig("uri_a").getUri());

        assertEquals(true, taglibManager.hasTaglibConfig("uri_b"));
        assertEquals("uri_b", taglibManager.getTaglibConfig("uri_b").getUri());

        assertEquals(false, taglibManager.hasTaglibConfig("uri_c"));
        try {
            taglibManager.getTaglibConfig("uri_c");
            fail();
        } catch (UriNotFoundRuntimeException e) {
        }
    }

    private static class MockTaglibManager implements TaglibManager {

        private TaglibConfig taglibConfig;

        public TaglibConfig getTaglibConfig(String uri)
                throws UriNotFoundRuntimeException {
            return taglibConfig;
        }

        public void setTaglibConfig(TaglibConfig taglibConfig) {
            this.taglibConfig = taglibConfig;
        }

        public boolean hasTaglibConfig(String uri) {
            if (taglibConfig == null) {
                return false;
            }
            return uri.equals(taglibConfig.getUri());
        }
    }

    private static class MockTaglibConfig implements TaglibConfig {

        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public TagConfig getTagConfig(String name)
                throws TagNotFoundRuntimeException {
            return null;
        }

        public void addTagConfig(TagConfig tagConfig) {
        }

    }

}
