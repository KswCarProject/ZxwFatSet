package com.hp.hpl.sparta.xpath;

public class TrueExpr extends BooleanExpr {
    static final TrueExpr INSTANCE = new TrueExpr();

    public String toString() {
        return "";
    }

    private TrueExpr() {
    }

    public void accept(BooleanExprVisitor booleanExprVisitor) {
        booleanExprVisitor.visit(this);
    }
}
