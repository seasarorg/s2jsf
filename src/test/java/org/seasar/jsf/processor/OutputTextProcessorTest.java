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
package org.seasar.jsf.processor;

import org.seasar.extension.unit.S2TestCase;

/**
 * @author yone
 */
public class OutputTextProcessorTest extends S2TestCase {

    public void testAddChild_TextProcessor() throws Exception {
        // # Arrange #
        OutputTextProcessor outputText = new OutputTextProcessor("h:outputText");
        TextProcessor text = new TextProcessor();

        // # Act #
        outputText.addChild(text);

        // # Assert #
        assertEquals(0, outputText.getChildCount());
    }

    public void testAddChild_TextProcessor2() throws Exception {
        // # Arrange #
        OutputTextProcessor outputText = new OutputTextProcessor("h:outputText");
        TagProcessorImpl tag = new TagProcessorImpl();

        // # Act #
        outputText.addChild(tag);

        // # Assert #
        assertEquals(1, outputText.getChildCount());
    }

}