package com.hp.hpl.sparta.xpath;

public class AllElementTest extends NodeTest {
    static final AllElementTest INSTANCE = new AllElementTest();

    public boolean isStringValue() {
        return false;
    }

    public String toString() {
        return "*";
    }

    private AllElementTest() {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
