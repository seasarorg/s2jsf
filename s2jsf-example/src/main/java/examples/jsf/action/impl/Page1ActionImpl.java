package examples.jsf.action.impl;

import examples.jsf.action.Page1Action;
import examples.jsf.exception.ErrorPage1RuntimeException;

public class Page1ActionImpl implements Page1Action {

	public String throwException() {
		throw new ErrorPage1RuntimeException("errorPage1");
	}

}
