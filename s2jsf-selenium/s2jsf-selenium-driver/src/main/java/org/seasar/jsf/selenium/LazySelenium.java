package org.seasar.jsf.selenium;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class LazySelenium implements Selenium {

    private Selenium _selenium;
    private boolean _completed;

    public void chooseCancelOnNextConfirmation() {
        getSelenium().chooseCancelOnNextConfirmation();
    }

    public void click(String field) {
        getSelenium().click(field);
    }

    public void clickAndWait(String field) {
        getSelenium().clickAndWait(field);
    }

    public String[] getAllButtons() {
        return getSelenium().getAllButtons();
    }

    public String[] getAllFields() {
        return getSelenium().getAllFields();
    }

    public String[] getAllLinks() {
        return getSelenium().getAllLinks();
    }

    public void open(String path) {
        getSelenium().open(path);
    }

    public void pause(int duration) {
        getSelenium().pause(duration);
    }

    public void selectAndWait(String field, String value) {
        getSelenium().selectAndWait(field, value);
    }

    public void selectWindow(String window) {
        getSelenium().selectWindow(window);
    }

    public void setContext(String context) {
        getSelenium().setContext(context);
    }

    public void setTextField(String field, String value) {
        getSelenium().setTextField(field, value);
    }

    public void start() {
        _selenium.start();
        _start = true;
    }

    public void stop() {
        _selenium.stop();
        _start = false;
    }

    public void storeText(String element, String value) {
        getSelenium().storeText(element, value);
    }

    public void storeValue(String field, String value) {
        getSelenium().storeValue(field, value);
    }

    public void testComplete() {
        getSelenium().testComplete();
        _completed = true;
    }

    public void type(String field, String value) {
        getSelenium().type(field, value);
    }

    public void typeAndWait(String field, String value) {
        getSelenium().typeAndWait(field, value);
    }

    public void verifyAlert(String alert) {
        getSelenium().verifyAlert(alert);
    }

    public void verifyAttribute(String element, String value) {
        getSelenium().verifyAttribute(element, value);
    }

    public void verifyConfirmation(String confirmation) {
        getSelenium().verifyConfirmation(confirmation);
    }

    public void verifyElementNotPresent(String type) {
        getSelenium().verifyElementNotPresent(type);
    }

    public void verifyElementPresent(String type) {
        getSelenium().verifyElementPresent(type);
    }

    public void verifyLocation(String location) {
        getSelenium().verifyLocation(location);
    }

    public void verifySelected(String field, String value) {
        getSelenium().verifySelected(field, value);
    }

    public void verifySelectOptions(String field, String[] values) {
        getSelenium().verifySelectOptions(field, values);
    }

    public void verifyTable(String table, String value) {
        getSelenium().verifyTable(table, value);
    }

    public void verifyText(String type, String text) {
        getSelenium().verifyText(type, text);
    }

    public void verifyTextPresent(String text) {
        getSelenium().verifyTextPresent(text);
    }

    public void verifyTitle(String title) {
        getSelenium().verifyTitle(title);
    }

    public void verifyValue(String field, String value) {
        getSelenium().verifyValue(field, value);
    }

    public void setSelenium(Selenium selenium) {
        _selenium = selenium;
    }

    private boolean _start;

    protected Selenium getSelenium() {
        if (_selenium == null) {
            throw new NullPointerException("selenium is null");
        }
        if (!isStart()) {
            start();
        }
        if (isCompleted()) {
            throw new SeleniumException("already completed");
        }
        return _selenium;
    }

    public boolean isStart() {
        return _start;
    }

    public boolean isCompleted() {
        return _completed;
    }

}
