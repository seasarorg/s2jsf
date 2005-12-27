package it00009;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ConverterDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date dateTime_ = new Date();
    private Date dateTime2_ = new Date();
    private BigDecimal bigDecimal_ = new BigDecimal("10");
    private BigInteger bigInteger_ = new BigInteger("112");
    private Byte byte_ = new Byte("1");
    private Character character_ = new Character('a');
    private Double double_ = new Double("1.2");
    private Float float_ = new Float("1.3");
    private Integer integer_ = new Integer("1");
    private Long long_ = new Long("10");
    private Number number_ = new Integer("-1");
    private Short short_ = new Short("23");

    public Integer getInteger() {
        return integer_;
    }

    public void setInteger(Integer integer) {
        integer_ = integer;
    }

    public Long getLong() {
        return long_;
    }

    public void setLong(Long l) {
        long_ = l;
    }

    public Number getNumber() {
        return number_;
    }

    public void setNumber(Number number) {
        number_ = number;
    }

    public Short getShort() {
        return short_;
    }

    public void setShort(Short s) {
        short_ = s;
    }

    public Date getDateTime() {
        return dateTime_;
    }

    public void setDateTime(Date dateTime) {
        dateTime_ = dateTime;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal_;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        bigDecimal_ = bigDecimal;
    }

    public BigInteger getBigInteger() {
        return bigInteger_;
    }

    public void setBigInteger(BigInteger bigInteger) {
        bigInteger_ = bigInteger;
    }

    public Byte getByte() {
        return byte_;
    }

    public void setByte(Byte b) {
        byte_ = b;
    }

    public Character getCharacter() {
        return character_;
    }

    public void setCharacter(Character character) {
        character_ = character;
    }

    public Double getDouble() {
        return double_;
    }

    public void setDouble(Double d) {
        double_ = d;
    }

    public Float getFloat() {
        return float_;
    }

    public void setFloat(Float f) {
        float_ = f;
    }

    public Date getDateTime2() {
        return dateTime2_;
    }

    public void setDateTime2(Date dateTime2) {
        dateTime2_ = dateTime2;
    }

}
