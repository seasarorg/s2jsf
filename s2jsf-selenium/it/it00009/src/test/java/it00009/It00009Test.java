package it00009;

import junit.framework.Test;

import org.seasar.jsf.selenium.SeleneseTestCase;
import org.seasar.jsf.selenium.WebApplicationTestSetup;

import com.thoughtworks.selenium.Selenium;

/*
 * [#7699]
 * 
 * convertエラー時に、idではなくlabalからエラーメッセージを構築する。
 */
public class It00009Test extends SeleneseTestCase {

    public static Test suite() {
        WebApplicationTestSetup testSetup = new WebApplicationTestSetup(
            It00009Test.class);
        return testSetup;
    }

    public void testValidation() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/a.html");
        {
            selenium.type("form:dateTime", "bbb");
            selenium.type("form:dateTime2", "bbb2");
            selenium.type("form:bigDecimal", "ccc");
            selenium.type("form:bigInteger", "ddd");
            selenium.type("form:byte", "eee");
            selenium.type("form:character", "fff");
            selenium.type("form:double", "ggg");
            selenium.type("form:float", "hhh");
            selenium.type("form:integer", "iii");
            selenium.type("form:long", "jjj");
            selenium.type("form:number", "kkk");
            selenium.type("form:short", "lll");
            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyText("message_a", "REQUIRED[あ]");
            selenium.verifyText("message_dateTime",
                "DateTimeConverter[bbb][でーとたいむ]");
            selenium.verifyText("message_dateTime2",
            "DateTimeConverter[bbb2][でーとたいむ2]");
            selenium.verifyText("message_bigDecimal",
                "BigDecimalConverter[びっぐでしまる][ccc]");
            selenium.verifyText("message_bigInteger",
                "BigIntegerConverter[びっぐいんてじゃ][ddd]");
            selenium.verifyText("message_byte", "ByteConverter[eee][ばいと]");
            selenium.verifyText("message_character",
                "CharacterConverter[fff][きゃらくた]");
            selenium.verifyText("message_double", "DoubleConverter[だぶる][ggg]");
            selenium.verifyText("message_float", "FloatConverter[ふろーと][hhh]");
            selenium.verifyText("message_integer", "IntegerConverter[いんてじゃ][iii]");
            selenium.verifyText("message_long", "LongConverter[ろんぐ][jjj]");
            selenium.verifyText("message_number", "NumberConverter[なんば][kkk]");
            selenium.verifyText("message_short", "ShortConverter[しょーと][lll]");
        }
        selenium.testComplete();
    }

    public void testBoolean() throws Exception {
        // ## Arrange ##
        Selenium selenium = getSelenium();

        // ## Act ##
        // ## Assert ##
        selenium.open("/boolean.html");
        {
            selenium.type("form:boolean", "");
            selenium.type("form:primitiveBoolean", "");
            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:boolean", "");
            selenium.verifyValue("form:primitiveBoolean", "false");

            selenium.type("form:boolean", "1");
            selenium.type("form:primitiveBoolean", "1");
            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:boolean", "true");
            selenium.verifyValue("form:primitiveBoolean", "true");

            selenium.type("form:boolean", "0");
            selenium.type("form:primitiveBoolean", "0");
            selenium.clickAndWait("doSubmit");
        }
        {
            selenium.verifyValue("form:boolean", "false");
            selenium.verifyValue("form:primitiveBoolean", "false");
        }
    }

}
