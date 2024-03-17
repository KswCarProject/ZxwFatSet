package com.hp.hpl.sparta.xpath;

public abstract class AttrRelationalExpr extends AttrExpr {
    private final int attrValue_;

    AttrRelationalExpr(String str, int i) {
        super(str);
        this.attrValue_ = i;
    }

    public double getAttrValue() {
        return (double) this.attrValue_;
    }

    /* access modifiers changed from: protected */
    public String toString(String str) {
        return "[" + super.toString() + str + "'" + this.attrValue_ + "']";
    }
}
