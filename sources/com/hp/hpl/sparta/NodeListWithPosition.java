package com.hp.hpl.sparta;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* compiled from: XPathVisitor */
class NodeListWithPosition {
    private static final Integer EIGHT = new Integer(8);
    private static final Integer FIVE = new Integer(5);
    private static final Integer FOUR = new Integer(4);
    private static final Integer NINE = new Integer(9);
    private static final Integer ONE = new Integer(1);
    private static final Integer SEVEN = new Integer(7);
    private static final Integer SIX = new Integer(6);
    private static final Integer TEN = new Integer(10);
    private static final Integer THREE = new Integer(3);
    private static final Integer TWO = new Integer(2);
    private Hashtable positions_ = new Hashtable();
    private final Vector vector_ = new Vector();

    NodeListWithPosition() {
    }

    /* access modifiers changed from: package-private */
    public Enumeration iterator() {
        return this.vector_.elements();
    }

    /* access modifiers changed from: package-private */
    public void removeAllElements() {
        this.vector_.removeAllElements();
        this.positions_.clear();
    }

    /* access modifiers changed from: package-private */
    public void add(String str) {
        this.vector_.addElement(str);
    }

    private static Integer identity(Node node) {
        return new Integer(System.identityHashCode(node));
    }

    /* access modifiers changed from: package-private */
    public void add(Node node, int i) {
        Integer num;
        this.vector_.addElement(node);
        switch (i) {
            case 1:
                num = ONE;
                break;
            case 2:
                num = TWO;
                break;
            case 3:
                num = THREE;
                break;
            case 4:
                num = FOUR;
                break;
            case 5:
                num = FIVE;
                break;
            case 6:
                num = SIX;
                break;
            case 7:
                num = SEVEN;
                break;
            case 8:
                num = EIGHT;
                break;
            case 9:
                num = NINE;
                break;
            case 10:
                num = TEN;
                break;
            default:
                num = new Integer(i);
                break;
        }
        this.positions_.put(identity(node), num);
    }

    /* access modifiers changed from: package-private */
    public int position(Node node) {
        return ((Integer) this.positions_.get(identity(node))).intValue();
    }

    public String toString() {
        try {
            StringBuffer stringBuffer = new StringBuffer("{ ");
            Enumeration elements = this.vector_.elements();
            while (elements.hasMoreElements()) {
                Object nextElement = elements.nextElement();
                if (nextElement instanceof String) {
                    stringBuffer.append("String(" + nextElement + ") ");
                } else {
                    Node node = (Node) nextElement;
                    stringBuffer.append("Node(" + node.toXml() + ")[" + this.positions_.get(identity(node)) + "] ");
                }
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        } catch (IOException e) {
            return e.toString();
        }
    }
}
