package com.hp.hpl.sparta.xpath;

public class AttrTest extends NodeTest {
    private final String attrName_;

    public boolean isStringValue() {
        return true;
    }

    AttrTest(String str) {
        this.attrName_ = str;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getAttrName() {
        return this.attrName_;
    }

    public String toString() {
        return "@" + this.attrName_;
    }
}
