package org.seasar.jsf.component;

import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;

import junit.framework.TestCase;

import org.seasar.jsf.mock.MockFacesContext;
import org.seasar.jsf.mock.MockValueBinding;
import org.seasar.teeda.core.mock.MockApplicationImpl;

public class S2UISelectItemsTest extends TestCase {

    public void test1() throws Exception {
        S2UISelectItems items = new S2UISelectItems();
        items.setNullLabel("aaa");
        assertEquals("aaa", items.getNullLabel());
    }

    public void test2() throws Exception {
        S2UISelectItems items = new S2UISelectItems();
        items.setNullLabel("#{aaa.bbb}");
        final MockFacesContext context = new MockFacesContext();
        context.setApplication(new MockApplicationImpl() {

            public ValueBinding createValueBinding(String arg0)
                    throws ReferenceSyntaxException {
                MockValueBinding binding = new MockValueBinding(arg0);
                binding.setValue(context, "hoge");
                return binding;
            }

        });
        assertEquals("hoge", items.getNullLabel());
    }

}
