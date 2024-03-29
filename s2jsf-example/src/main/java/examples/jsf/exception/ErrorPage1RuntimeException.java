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
package examples.jsf.exception;

public class ErrorPage1RuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ErrorPage1RuntimeException() {
		super();
	}

	/**
	 * @param message
	 */
	public ErrorPage1RuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ErrorPage1RuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorPage1RuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
