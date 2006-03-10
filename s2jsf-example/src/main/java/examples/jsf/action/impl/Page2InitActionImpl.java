package examples.jsf.action.impl;

import examples.jsf.action.Page2InitAction;
import examples.jsf.exception.ErrorPage2RuntimeException;

public class Page2InitActionImpl implements Page2InitAction {

	public String initialize() {
		throw new ErrorPage2RuntimeException("errorPage2");
	}

}
