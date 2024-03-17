package com.hp.hpl.sparta.xpath;

public class TextExistsExpr extends BooleanExpr {
    static final TextExistsExpr INSTANCE = new TextExistsExpr();

    public String toString() {
        return "[text()]";
    }

    private TextExistsExpr() {
    }

    public void accept(BooleanExprVisitor booleanExprVisitor) throws XPathException {
        booleanExprVisitor.visit(this);
    }
}
