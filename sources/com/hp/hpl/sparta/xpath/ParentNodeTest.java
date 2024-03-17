package com.hp.hpl.sparta.xpath;

public class ParentNodeTest extends NodeTest {
    static final ParentNodeTest INSTANCE = new ParentNodeTest();

    public boolean isStringValue() {
        return false;
    }

    public String toString() {
        return "..";
    }

    private ParentNodeTest() {
    }

    public void accept(Visitor visitor) throws XPathException {
        visitor.visit(this);
    }
}
