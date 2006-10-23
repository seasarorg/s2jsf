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
package org.seasar.jsf.lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.jsf.exception.PathNotFoundRuntimeException;
import org.seasar.jsf.mock.MockFacesContext;

/**
 * @author manhole
 * @author yone
 */
public class LifecycleImplTest extends S2TestCase {

    protected void setUp() throws Exception {
        include("jsf.dicon");
        include("jsfErrorPage.dicon");
    }

    public void testBeforePhase() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        LifecycleImpl lifecycle = new LifecycleImpl();
        lifecycle.addPhaseListener(new SomePhaseListener() {
            public void beforePhase(PhaseEvent event) {
                callSeq.add("A");
            }
        });
        lifecycle.addPhaseListener(new SomePhaseListener() {
            public void beforePhase(PhaseEvent event) {
                callSeq.add("B");
            }
        });
        lifecycle.addPhaseListener(new SomePhaseListener() {
            public void beforePhase(PhaseEvent event) {
                callSeq.add("C");
            }
        });

        // ## Act ##
        lifecycle.beforePhase(new NullFacesContext(),
                PhaseId.APPLY_REQUEST_VALUES);

        // ## Assert ##
        assertEquals(3, callSeq.size());
        Iterator it = callSeq.iterator();
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
    }

    public void testAfterPhase() throws Exception {
        // ## Arrange ##
        final List callSeq = new ArrayList();
        LifecycleImpl lifecycle = new LifecycleImpl();
        lifecycle.addPhaseListener(new SomePhaseListener() {
            public void afterPhase(PhaseEvent event) {
                callSeq.add("A");
            }
        });
        lifecycle.addPhaseListener(new SomePhaseListener() {
            public void afterPhase(PhaseEvent event) {
                callSeq.add("B");
            }
        });
        lifecycle.addPhaseListener(new SomePhaseListener() {
            public void afterPhase(PhaseEvent event) {
                callSeq.add("C");
            }
        });

        // ## Act ##
        lifecycle.afterPhase(new NullFacesContext(),
                PhaseId.APPLY_REQUEST_VALUES);

        // ## Assert ##
        assertEquals(3, callSeq.size());
        Iterator it = callSeq.iterator();
        assertEquals("C", it.next());
        assertEquals("B", it.next());
        assertEquals("A", it.next());
    }

    public void testHandleExceptionRegistJsfErrorDicon() throws Exception {
        // ## Arrange ##
        LifecycleImpl lifecycle = new LifecycleImpl() {
            protected boolean restoreView(FacesContext context)
                    throws FacesException {
                throw new PathNotFoundRuntimeException("hoge.html");
            }
        };
        // ## Act & Assert ##
        try {
            lifecycle.execute(new MockFacesContext());
        } catch (Exception e) {
            fail();
        }
    }

    public void testHandleExceptionNotRegistJsfErrorDicon() throws Exception {
        // ## Arrange ##
        LifecycleImpl lifecycle = new LifecycleImpl() {
            protected boolean restoreView(FacesContext context)
                    throws FacesException {
                throw new RuntimeException("dummy");
            }
        };
        // ## Act & Assert ##
        try {
            lifecycle.execute(new MockFacesContext());
            fail();
        } catch (Exception e) {
            assertEquals("dummy", e.getMessage());
        }

    }

    private static class SomePhaseListener implements PhaseListener {

        private static final long serialVersionUID = 1L;

        public void afterPhase(PhaseEvent event) {
            throw new RuntimeException();
        }

        public void beforePhase(PhaseEvent event) {
            throw new RuntimeException();
        }

        public PhaseId getPhaseId() {
            return PhaseId.ANY_PHASE;
        }
    }

    private static class NullFacesContext extends FacesContext {

        public Application getApplication() {
            return null;
        }

        public Iterator getClientIdsWithMessages() {
            return null;
        }

        public ExternalContext getExternalContext() {
            return null;
        }

        public Severity getMaximumSeverity() {
            return null;
        }

        public Iterator getMessages() {
            return null;
        }

        public Iterator getMessages(String clientId) {
            return null;
        }

        public RenderKit getRenderKit() {
            return null;
        }

        public boolean getRenderResponse() {
            return false;
        }

        public boolean getResponseComplete() {
            return false;
        }

        public ResponseStream getResponseStream() {
            return null;
        }

        public void setResponseStream(ResponseStream responseStream) {
        }

        public ResponseWriter getResponseWriter() {
            return null;
        }

        public void setResponseWriter(ResponseWriter responseWriter) {
        }

        public UIViewRoot getViewRoot() {
            return null;
        }

        public void setViewRoot(UIViewRoot root) {
        }

        public void addMessage(String clientId, FacesMessage message) {
        }

        public void release() {
        }

        public void renderResponse() {
        }

        public void responseComplete() {
        }

    }

}
