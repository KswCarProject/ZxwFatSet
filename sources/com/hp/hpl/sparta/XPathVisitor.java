package com.hp.hpl.sparta;

import com.hp.hpl.sparta.xpath.AllElementTest;
import com.hp.hpl.sparta.xpath.AttrEqualsExpr;
import com.hp.hpl.sparta.xpath.AttrExistsExpr;
import com.hp.hpl.sparta.xpath.AttrGreaterExpr;
import com.hp.hpl.sparta.xpath.AttrLessExpr;
import com.hp.hpl.sparta.xpath.AttrNotEqualsExpr;
import com.hp.hpl.sparta.xpath.AttrTest;
import com.hp.hpl.sparta.xpath.BooleanExpr;
import com.hp.hpl.sparta.xpath.ElementTest;
import com.hp.hpl.sparta.xpath.ParentNodeTest;
import com.hp.hpl.sparta.xpath.PositionEqualsExpr;
import com.hp.hpl.sparta.xpath.Step;
import com.hp.hpl.sparta.xpath.TextEqualsExpr;
import com.hp.hpl.sparta.xpath.TextExistsExpr;
import com.hp.hpl.sparta.xpath.TextNotEqualsExpr;
import com.hp.hpl.sparta.xpath.TextTest;
import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.hp.hpl.sparta.xpath.TrueExpr;
import com.hp.hpl.sparta.xpath.Visitor;
import com.hp.hpl.sparta.xpath.XPath;
import com.hp.hpl.sparta.xpath.XPathException;
import java.util.Enumeration;
import java.util.Vector;

class XPathVisitor implements Visitor {
    private static final Boolean FALSE = new Boolean(false);
    private static final Boolean TRUE = new Boolean(true);
    private Node contextNode_;
    private final BooleanStack exprStack_;
    private boolean multiLevel_;
    private Object node_;
    private Vector nodelistFiltered_;
    private final NodeListWithPosition nodelistRaw_;
    private Enumeration nodesetIterator_;
    private XPath xpath_;

    private XPathVisitor(XPath xPath, Node node) throws XPathException {
        this.nodelistRaw_ = new NodeListWithPosition();
        this.nodelistFiltered_ = new Vector();
        this.nodesetIterator_ = null;
        this.node_ = null;
        this.exprStack_ = new BooleanStack();
        this.xpath_ = xPath;
        this.contextNode_ = node;
        Vector vector = new Vector(1);
        this.nodelistFiltered_ = vector;
        vector.addElement(this.contextNode_);
        Enumeration steps = xPath.getSteps();
        while (steps.hasMoreElements()) {
            Step step = (Step) steps.nextElement();
            this.multiLevel_ = step.isMultiLevel();
            this.nodesetIterator_ = null;
            step.getNodeTest().accept(this);
            this.nodesetIterator_ = this.nodelistRaw_.iterator();
            this.nodelistFiltered_.removeAllElements();
            BooleanExpr predicate = step.getPredicate();
            while (this.nodesetIterator_.hasMoreElements()) {
                this.node_ = this.nodesetIterator_.nextElement();
                predicate.accept(this);
                if (this.exprStack_.pop().booleanValue()) {
                    this.nodelistFiltered_.addElement(this.node_);
                }
            }
        }
    }

    public XPathVisitor(Element element, XPath xPath) throws XPathException {
        this(xPath, (Node) element);
        if (xPath.isAbsolute()) {
            throw new XPathException(xPath, "Cannot use element as context node for absolute xpath");
        }
    }

    public XPathVisitor(Document document, XPath xPath) throws XPathException {
        this(xPath, (Node) document);
    }

    public void visit(ThisNodeTest thisNodeTest) {
        this.nodelistRaw_.removeAllElements();
        this.nodelistRaw_.add(this.contextNode_, 1);
    }

    public void visit(ParentNodeTest parentNodeTest) throws XPathException {
        this.nodelistRaw_.removeAllElements();
        Element parentNode = this.contextNode_.getParentNode();
        if (parentNode != null) {
            this.nodelistRaw_.add(parentNode, 1);
            return;
        }
        throw new XPathException(this.xpath_, "Illegal attempt to apply \"..\" to node with no parent.");
    }

    public void visit(AllElementTest allElementTest) {
        Vector vector = this.nodelistFiltered_;
        this.nodelistRaw_.removeAllElements();
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            Object nextElement = elements.nextElement();
            if (nextElement instanceof Element) {
                accumulateElements((Element) nextElement);
            } else if (nextElement instanceof Document) {
                accumulateElements((Document) nextElement);
            }
        }
    }

    private void accumulateElements(Document document) {
        Element documentElement = document.getDocumentElement();
        this.nodelistRaw_.add(documentElement, 1);
        if (this.multiLevel_) {
            accumulateElements(documentElement);
        }
    }

    private void accumulateElements(Element element) {
        int i = 0;
        for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof Element) {
                i++;
                this.nodelistRaw_.add(firstChild, i);
                if (this.multiLevel_) {
                    accumulateElements((Element) firstChild);
                }
            }
        }
    }

    public void visit(TextTest textTest) {
        Vector vector = this.nodelistFiltered_;
        this.nodelistRaw_.removeAllElements();
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            Object nextElement = elements.nextElement();
            if (nextElement instanceof Element) {
                for (Node firstChild = ((Element) nextElement).getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                    if (firstChild instanceof Text) {
                        this.nodelistRaw_.add(((Text) firstChild).getData());
                    }
                }
            }
        }
    }

    public void visit(ElementTest elementTest) {
        String tagName = elementTest.getTagName();
        Vector vector = this.nodelistFiltered_;
        int size = vector.size();
        this.nodelistRaw_.removeAllElements();
        for (int i = 0; i < size; i++) {
            Object elementAt = vector.elementAt(i);
            if (elementAt instanceof Element) {
                accumulateMatchingElements((Element) elementAt, tagName);
            } else if (elementAt instanceof Document) {
                accumulateMatchingElements((Document) elementAt, tagName);
            }
        }
    }

    private void accumulateMatchingElements(Document document, String str) {
        Element documentElement = document.getDocumentElement();
        if (documentElement != null) {
            if (documentElement.getTagName() == str) {
                this.nodelistRaw_.add(documentElement, 1);
            }
            if (this.multiLevel_) {
                accumulateMatchingElements(documentElement, str);
            }
        }
    }

    private void accumulateMatchingElements(Element element, String str) {
        int i = 0;
        for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof Element) {
                Element element2 = (Element) firstChild;
                if (element2.getTagName() == str) {
                    i++;
                    this.nodelistRaw_.add(element2, i);
                }
                if (this.multiLevel_) {
                    accumulateMatchingElements(element2, str);
                }
            }
        }
    }

    public void visit(AttrTest attrTest) {
        String attribute;
        Vector vector = this.nodelistFiltered_;
        this.nodelistRaw_.removeAllElements();
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            Node node = (Node) elements.nextElement();
            if ((node instanceof Element) && (attribute = ((Element) node).getAttribute(attrTest.getAttrName())) != null) {
                this.nodelistRaw_.add(attribute);
            }
        }
    }

    public void visit(TrueExpr trueExpr) {
        this.exprStack_.push(TRUE);
    }

    public void visit(AttrExistsExpr attrExistsExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            String attribute = ((Element) obj).getAttribute(attrExistsExpr.getAttrName());
            this.exprStack_.push(attribute != null && attribute.length() > 0 ? TRUE : FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(AttrEqualsExpr attrEqualsExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            this.exprStack_.push(attrEqualsExpr.getAttrValue().equals(((Element) obj).getAttribute(attrEqualsExpr.getAttrName())) ? TRUE : FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(AttrNotEqualsExpr attrNotEqualsExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            this.exprStack_.push(attrNotEqualsExpr.getAttrValue().equals(((Element) obj).getAttribute(attrNotEqualsExpr.getAttrName())) ^ true ? TRUE : FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(AttrLessExpr attrLessExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            this.exprStack_.push((((double) Long.parseLong(((Element) obj).getAttribute(attrLessExpr.getAttrName()))) > attrLessExpr.getAttrValue() ? 1 : (((double) Long.parseLong(((Element) obj).getAttribute(attrLessExpr.getAttrName()))) == attrLessExpr.getAttrValue() ? 0 : -1)) < 0 ? TRUE : FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(AttrGreaterExpr attrGreaterExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            this.exprStack_.push((((double) Long.parseLong(((Element) obj).getAttribute(attrGreaterExpr.getAttrName()))) > attrGreaterExpr.getAttrValue() ? 1 : (((double) Long.parseLong(((Element) obj).getAttribute(attrGreaterExpr.getAttrName()))) == attrGreaterExpr.getAttrValue() ? 0 : -1)) > 0 ? TRUE : FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(TextExistsExpr textExistsExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            for (Node firstChild = ((Element) obj).getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                if (firstChild instanceof Text) {
                    this.exprStack_.push(TRUE);
                    return;
                }
            }
            this.exprStack_.push(FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(TextEqualsExpr textEqualsExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            Node firstChild = ((Element) obj).getFirstChild();
            while (firstChild != null) {
                if (!(firstChild instanceof Text) || !((Text) firstChild).getData().equals(textEqualsExpr.getValue())) {
                    firstChild = firstChild.getNextSibling();
                } else {
                    this.exprStack_.push(TRUE);
                    return;
                }
            }
            this.exprStack_.push(FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(TextNotEqualsExpr textNotEqualsExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            Node firstChild = ((Element) obj).getFirstChild();
            while (firstChild != null) {
                if (!(firstChild instanceof Text) || ((Text) firstChild).getData().equals(textNotEqualsExpr.getValue())) {
                    firstChild = firstChild.getNextSibling();
                } else {
                    this.exprStack_.push(TRUE);
                    return;
                }
            }
            this.exprStack_.push(FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test attribute of document");
    }

    public void visit(PositionEqualsExpr positionEqualsExpr) throws XPathException {
        Object obj = this.node_;
        if (obj instanceof Element) {
            this.exprStack_.push(this.nodelistRaw_.position((Element) obj) == positionEqualsExpr.getPosition() ? TRUE : FALSE);
            return;
        }
        throw new XPathException(this.xpath_, "Cannot test position of document");
    }

    public Enumeration getResultEnumeration() {
        return this.nodelistFiltered_.elements();
    }

    public Element getFirstResultElement() {
        if (this.nodelistFiltered_.size() == 0) {
            return null;
        }
        return (Element) this.nodelistFiltered_.elementAt(0);
    }

    public String getFirstResultString() {
        if (this.nodelistFiltered_.size() == 0) {
            return null;
        }
        return this.nodelistFiltered_.elementAt(0).toString();
    }

    private static class BooleanStack {
        private Item top_;

        private BooleanStack() {
            this.top_ = null;
        }

        private static class Item {
            final Boolean bool;
            final Item prev;

            Item(Boolean bool2, Item item) {
                this.bool = bool2;
                this.prev = item;
            }
        }

        /* access modifiers changed from: package-private */
        public void push(Boolean bool) {
            this.top_ = new Item(bool, this.top_);
        }

        /* access modifiers changed from: package-private */
        public Boolean pop() {
            Boolean bool = this.top_.bool;
            this.top_ = this.top_.prev;
            return bool;
        }
    }
}
