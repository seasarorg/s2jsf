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
package org.seasar.jsf;

import org.seasar.framework.util.ArrayUtil;

/**
 * @author higa
 * 
 */
public interface JsfConstants {

    public String MAYA_NSURI = "http://www.seasar.org/maya";

    public String JSF_HTML_URI = "http://java.sun.com/jsf/html";

    public String JSF_CORE_URI = "http://java.sun.com/jsf/core";

    public String S2JSF_URI = "http://www.seasar.org/jsf";

    public String LINE_SP = System.getProperty("line.separator");

    public String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    public String FORWARD_SEEN = "javax.servlet.forward.seen";

    public String JSP_EXCEPTION = "javax.servlet.jsp.jspException";

    public String SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";

    public String SERVLET_ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    public String SERVLET_ERROR_EXCEPTION_MESSAGE = "javax.servlet.error.message";

    public String ERROR_EXCEPTION = "seasar.jsf.error.exception";

    public String ERROR_EXCEPTION_TYPE = "seasar.jsf.error.exception_type";

    public String ERROR_MESSAGE = "seasar.jsf.error.message";

    public String CHARSET = "charset";

    public String CONTENT_TYPE = "Content-Type";

    public String EQUAL = "=";

    public int K = 1024;

    public int DEFAULT_BUFFER_SIZE = 8 * K;

    public String JSF_BINDING_START = "#{";

    public String JSF_BINDING_END = "}";

    public char JS_STMT_END = ';';

    public String HTML_ELEM = "html";

    public String TITLE_ELEM = "title";

    public String BODY_ELEM = "body";

    public String LINK_ELEM = "link";

    public String META_ELEM = "meta";

    public String ANCHOR_ELEM = "a";

    public String BR_ELEM = "br";

    public String SPAN_ELEM = "span";

    public String DIV_ELEM = "div";

    public String INPUT_ELEM = "input";

    public String FORM_ELEM = "form";

    public String BASE_ELEM = "base";

    public String SELECT_ELEM = "select";

    public String OPTION_ELEM = "option";

    public String OPTGROUP_ELEM = "optgroup";

    public String TEXTAREA_ELEM = "textarea";

    public String TABLE_ELEM = "table";

    public String TR_ELEM = "tr";

    public String TD_ELEM = "td";

    public String UL_ELEM = "ul";

    public String LI_ELEM = "li";

    public String ID_ATTR = "id";

    public String CLASS_ATTR = "class";

    public String METHOD_ATTR = "method";

    public String STYLE_CLASS_ATTR = "styleClass";

    public String INJECT_ATTR = "inject";

    public String CONTENT_ATTR = "content";

    public String HTTP_EQUIV_ATTR = "http-equiv";

    public String HREF_ATTR = "href";

    public String HREFLANG_ATTR = "hreflang";

    public String VALUE_ATTR = "value";

    public String ACTION_ATTR = "action";

    public String TYPE_ATTR = "type";

    public String ITEMS_ATTR = "items";

    public String ITEM_VALUE_ATTR = "itemValue";

    public String ITEM_LABEL_ATTR = "itemLabel";

    public String ITEM_DISABLED_ATTR = "itemDisabled";

    public String ITEM_DESCRIPTION_ATTR = "itemDescription";

    public String NULL_LABEL_ATTR = "nullLabel";

    public String LABEL_ATTR = "label";

    public String BINDING_ATTR = "binding";

    public String SELECTED_ATTR = "selected";

    public String CHECKED_ATTR = "checked";

    public String TARGET_ATTR = "target";

    public String DIR_ATTR = "dir";

    public String LANG_ATTR = "lang";

    public String STYLE_ATTR = "style";

    public String TITLE_ATTR = "title";

    public String ESCAPE_ATTR = "escape";

    public String REQUIRED_ATTR = "required";

    public String SIZE_ATTR = "size";

    public String MAXLENGTH_ATTR = "maxlength";

    public String READONLY_ATTR = "readonly";

    public String ACCEPT_ATTR = "accept";

    public String IMMEDIATE_ATTR = "immediate";

    public String NAME_ATTR = "name";

    public String TABINDEX_ATTR = "tabindex";

    public String DISABLED_ATTR = "disabled";

    public String BORDER_ATTR = "border";

    public String ACCESSKEY_ATTR = "accesskey";

    public String ALT_ATTR = "alt";

    public String DISABLED_CLASS_ATTR = "disabledClass";

    public String ENABLED_CLASS_ATTR = "enabledClass";

    public String LAYOUT_ATTR = "layout";

    public String ALIGN_ATTR = "align";

    public String DATAFLD_ATTR = "datafld";

    public String DATAFORMATAS_ATTR = "dataformatas";

    public String DATASRC_ATTR = "datasrc";

    public String CONVERTER_ATTR = "converter";

    public String MULTIPLE_ATTR = "multiple";

    public String TRANSIENT_ATTR = "transient";

    public String SRC_ATTR = "src";

    public String REL_ATTR = "rel";

    public String REV_ATTR = "rev";

    public String SHAPE_ATTR = "shape";

    public String MEDIA_ATTR = "media";

    public String CHARSET_ATTR = "charset";

    public String COORDS_ATTR = "coords";

    public String EXTENDS_ATTR = "extends";

    public String PASSTHROUGH_ATTR = "passthrough";

    public String COLS_ATTR = "cols";

    public String ROWS_ATTR = "rows";

    public String REDISPLAY_ATTR = "redisplay";

    public String ONCLICK_ATTR = "onclick";

    public String ONDBLCLICK_ATTR = "ondblclick";

    public String ONKEYDOWN_ATTR = "onkeydown";

    public String ONKEYPRESS_ATTR = "onkeypress";

    public String ONKEYUP_ATTR = "onkeyup";

    public String ONMOUSEDOWN_ATTR = "onmousedown";

    public String ONMOUSEMOVE_ATTR = "onmousemove";

    public String ONMOUSEOUT_ATTR = "onmouseout";

    public String ONMOUSEOVER_ATTR = "onmouseover";

    public String ONMOUSEUP_ATTR = "onmouseup";

    public String ONBLUR_ATTR = "onblur";

    public String ONCHANGE_ATTR = "onchange";

    public String ONFOCUS_ATTR = "onfocus";

    public String ONSELECT_ATTR = "onselect";

    public String PAGE_DIRECTION_ATTR = "pageDirection";

    public String ACCEPT_CHARSET_ATTR = "accept-charset";

    public String ENCTYPE_ATTR = "enctype";

    public String ONRESET_ATTR = "onreset";

    public String ONSUMBIT_ATTR = "onsubmit";

    public String ERROR_CLASS_ATTR = "errorClass";

    public String ERROR_STYLE_ATTR = "errorStyle";

    public String FATAL_CLASS_ATTR = "fatalClass";

    public String FATAL_STYLE_ATTR = "fatalStyle";

    public String INFO_CLASS_ATTR = "infoClass";

    public String INFO_STYLE_ATTR = "infoStyle";

    public String WARN_CLASS_ATTR = "warnClass";

    public String WARN_STYLE_ATTR = "warnStyle";

    public String TOOLTIP_ATTR = "tooltip";

    public String FOR_ATTR = "for";

    public String SHOW_SUMMARY_ATTR = "showSummary";

    public String SHOW_DETAIL_ATTR = "showDetail";

    public String SCRIPT_ELEM = "script";

    public String TEXT_VALUE = "text";

    public String SUBMIT_VALUE = "submit";

    public String BUTTON_VALUE = "button";

    public String CHECKBOX_VALUE = "checkbox";

    public String RADIO_VALUE = "radio";

    public String HIDDEN_VALUE = "hidden";

    public String PASSWORD_VALUE = "password";

    public String SELECTED_VALUE = "selected";

    public String DISABLED_VALUE = "disabled";

    public String CHECKED_VALUE = "checked";

    public String POST_VALUE = "post";

    public String NBSP_ENTITY = "&nbsp;";

    public String BR_TAG = "<br />";

    public String[] EVENT_HANDLER_ATTRIBUTES_WITHOUT_ONCLICK = {
            ONDBLCLICK_ATTR, ONMOUSEDOWN_ATTR, ONMOUSEUP_ATTR,
            ONMOUSEOVER_ATTR, ONMOUSEMOVE_ATTR, ONMOUSEOUT_ATTR,
            ONKEYPRESS_ATTR, ONKEYDOWN_ATTR, ONKEYUP_ATTR };

    public String[] EVENT_HANDLER_ATTRIBUTES = (String[]) ArrayUtil.add(
            EVENT_HANDLER_ATTRIBUTES_WITHOUT_ONCLICK,
            new String[] { ONCLICK_ATTR });

    public String[] UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE = { DIR_ATTR, LANG_ATTR,
            TITLE_ATTR, };

    public String[] UNIVERSAL_ATTRIBUTES = (String[]) ArrayUtil.add(
            UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE, new String[] { STYLE_ATTR,
                    STYLE_CLASS_ATTR });

    public String[] COMMON_PASSTROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            EVENT_HANDLER_ATTRIBUTES, UNIVERSAL_ATTRIBUTES);

    public String[] COMMON_PASSTROUGH_ATTRIBUTES_WITHOUT_STYLE = (String[]) ArrayUtil
            .add(EVENT_HANDLER_ATTRIBUTES, UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE);

    public String[] COMMON_FIELD_ATTRIBUTES_WITHOUT_DISABLED = {
            ACCESSKEY_ATTR, TABINDEX_ATTR };

    public String[] COMMON_FIELD_ATTRIBUTES = (String[]) ArrayUtil.add(
            COMMON_FIELD_ATTRIBUTES_WITHOUT_DISABLED,
            new String[] { DISABLED_ATTR });

    public String[] COMMON_FIELD_EVENT_ATTRIBUTES = { ONFOCUS_ATTR,
            ONBLUR_ATTR, ONSELECT_ATTR, ONCHANGE_ATTR };

    public String[] COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(ArrayUtil.add(COMMON_PASSTROUGH_ATTRIBUTES,
                    COMMON_FIELD_ATTRIBUTES_WITHOUT_DISABLED),
                    COMMON_FIELD_EVENT_ATTRIBUTES);

    public String[] ANCHOR_ATTRIBUTES = { ACCESSKEY_ATTR, CHARSET_ATTR,
            COORDS_ATTR, HREFLANG_ATTR, REL_ATTR, REV_ATTR, SHAPE_ATTR,
            TABINDEX_ATTR, TARGET_ATTR, TYPE_ATTR };

    public String[] ANCHOR_PASSTHROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            ANCHOR_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES);

    public String[] ANCHOR_PASSTHROUGH_ATTRIBUTES_WITHOUT_STYLE = (String[]) ArrayUtil
            .add(ANCHOR_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES_WITHOUT_STYLE);

    public String[] SELECT_ATTRIBUTES = { DATAFLD_ATTR, DATASRC_ATTR,
            DATAFORMATAS_ATTR, };

    public String[] SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(SELECT_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public String[] INPUT_ATTRIBUTES = { ALIGN_ATTR, ALT_ATTR, CHECKED_ATTR,
            DATAFLD_ATTR, DATASRC_ATTR, DATAFORMATAS_ATTR, MAXLENGTH_ATTR,
            READONLY_ATTR, SIZE_ATTR, };

    public String[] INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(INPUT_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public String[] FORM_ATTRIBUTES = { ACCEPT_ATTR, ACCEPT_CHARSET_ATTR,
            ENCTYPE_ATTR, ONRESET_ATTR, ONSUMBIT_ATTR, TARGET_ATTR, };

    public String[] FORM_PASSTHROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            FORM_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES);

    public String HIDDEN_COMMAND_INPUTS_SET_ATTR = "org.apache.myfaces.renderkit.html.HtmlFormRendererBase.HIDDEN_COMMAND_INPUTS_SET";

    public String HIDDEN_SUBMIT_INPUT_SUFFIX = "_SUBMIT";

    public String HIDDEN_SUBMIT_INPUT_VALUE = "1";

    public String[] MESSAGE_PASSTHROUGH_ATTRIBUTES_WITHOUT_TITLE_STYLE_AND_STYLE_CLASS = (String[]) ArrayUtil
            .add(new String[] { DIR_ATTR, LANG_ATTR }, EVENT_HANDLER_ATTRIBUTES);

}
