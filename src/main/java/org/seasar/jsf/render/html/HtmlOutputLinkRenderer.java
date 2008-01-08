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
package org.seasar.jsf.render.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.jsf.JsfConstants;
import org.seasar.jsf.util.RenderUtil;
import org.seasar.jsf.util.ValueHolderUtil;

/**
 * @author higa
 * 
 */
public class HtmlOutputLinkRenderer extends AbstractHtmlLinkRenderer {

    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        renderOutputLinkStart(context, (UIOutput) component);
    }

    protected void renderOutputLinkStart(FacesContext context, UIOutput output)
        throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String href = ValueHolderUtil.getValueAsString(context, output);
        if (output.getChildCount() > 0) {
            StringBuffer hrefBuf = new StringBuffer(href);
            addParametersToHref(output, hrefBuf, (href.indexOf('?') == -1),
                writer.getCharacterEncoding());
            href = hrefBuf.toString();
        }
        href = context.getExternalContext().encodeResourceURL(href);
        writer.startElement(JsfConstants.ANCHOR_ELEM, output);
        writer.writeAttribute(JsfConstants.ID_ATTR,
            output.getClientId(context), null);
        writer.writeURIAttribute(JsfConstants.HREF_ATTR, href, null);
        RenderUtil.renderAttributes(writer, output,
            JsfConstants.ANCHOR_PASSTHROUGH_ATTRIBUTES);
        writer.flush();
    }

}
