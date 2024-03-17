package com.hp.hpl.sparta.xpath;

public class ThisNodeTest extends NodeTest {
    static final ThisNodeTest INSTANCE = new ThisNodeTest();

    public boolean isStringValue() {
        return false;
    }

    public String toString() {
        return ".";
    }

    private ThisNodeTest() {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
