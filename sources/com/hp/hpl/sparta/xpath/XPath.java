package com.hp.hpl.sparta.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

public class XPath {
    private static final int ASSERTION = 0;
    private static Hashtable cache_ = new Hashtable();
    private boolean absolute_;
    private Stack steps_;
    private String string_;

    private XPath(boolean z, Step[] stepArr) {
        this.steps_ = new Stack();
        for (Step addElement : stepArr) {
            this.steps_.addElement(addElement);
        }
        this.absolute_ = z;
        this.string_ = null;
    }

    private XPath(String str) throws XPathException {
        this(str, (Reader) new InputStreamReader(new ByteArrayInputStream(str.getBytes())));
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x004b A[Catch:{ IOException -> 0x0072 }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0067 A[Catch:{ IOException -> 0x0072 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0068 A[Catch:{ IOException -> 0x0072 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private XPath(java.lang.String r6, java.io.Reader r7) throws com.hp.hpl.sparta.xpath.XPathException {
        /*
            r5 = this;
            r5.<init>()
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            r5.steps_ = r0
            r5.string_ = r6     // Catch:{ IOException -> 0x0072 }
            com.hp.hpl.sparta.xpath.SimpleStreamTokenizer r6 = new com.hp.hpl.sparta.xpath.SimpleStreamTokenizer     // Catch:{ IOException -> 0x0072 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x0072 }
            r7 = 47
            r6.ordinaryChar(r7)     // Catch:{ IOException -> 0x0072 }
            r0 = 46
            r6.ordinaryChar(r0)     // Catch:{ IOException -> 0x0072 }
            r0 = 58
            r6.wordChars(r0, r0)     // Catch:{ IOException -> 0x0072 }
            r0 = 95
            r6.wordChars(r0, r0)     // Catch:{ IOException -> 0x0072 }
            int r0 = r6.nextToken()     // Catch:{ IOException -> 0x0072 }
            r1 = 1
            r2 = 0
            if (r0 != r7) goto L_0x003a
            r5.absolute_ = r1     // Catch:{ IOException -> 0x0072 }
            int r0 = r6.nextToken()     // Catch:{ IOException -> 0x0072 }
            if (r0 != r7) goto L_0x003c
            r6.nextToken()     // Catch:{ IOException -> 0x0072 }
            r0 = r1
            goto L_0x003d
        L_0x003a:
            r5.absolute_ = r2     // Catch:{ IOException -> 0x0072 }
        L_0x003c:
            r0 = r2
        L_0x003d:
            java.util.Stack r3 = r5.steps_     // Catch:{ IOException -> 0x0072 }
            com.hp.hpl.sparta.xpath.Step r4 = new com.hp.hpl.sparta.xpath.Step     // Catch:{ IOException -> 0x0072 }
            r4.<init>(r5, r0, r6)     // Catch:{ IOException -> 0x0072 }
            r3.push(r4)     // Catch:{ IOException -> 0x0072 }
        L_0x0047:
            int r0 = r6.ttype     // Catch:{ IOException -> 0x0072 }
            if (r0 != r7) goto L_0x0062
            int r0 = r6.nextToken()     // Catch:{ IOException -> 0x0072 }
            if (r0 != r7) goto L_0x0056
            r6.nextToken()     // Catch:{ IOException -> 0x0072 }
            r0 = r1
            goto L_0x0057
        L_0x0056:
            r0 = r2
        L_0x0057:
            java.util.Stack r3 = r5.steps_     // Catch:{ IOException -> 0x0072 }
            com.hp.hpl.sparta.xpath.Step r4 = new com.hp.hpl.sparta.xpath.Step     // Catch:{ IOException -> 0x0072 }
            r4.<init>(r5, r0, r6)     // Catch:{ IOException -> 0x0072 }
            r3.push(r4)     // Catch:{ IOException -> 0x0072 }
            goto L_0x0047
        L_0x0062:
            int r7 = r6.ttype     // Catch:{ IOException -> 0x0072 }
            r0 = -1
            if (r7 != r0) goto L_0x0068
            return
        L_0x0068:
            com.hp.hpl.sparta.xpath.XPathException r7 = new com.hp.hpl.sparta.xpath.XPathException     // Catch:{ IOException -> 0x0072 }
            java.lang.String r0 = "at end of XPATH expression"
            java.lang.String r1 = "end of expression"
            r7.<init>(r5, r0, r6, r1)     // Catch:{ IOException -> 0x0072 }
            throw r7     // Catch:{ IOException -> 0x0072 }
        L_0x0072:
            r6 = move-exception
            com.hp.hpl.sparta.xpath.XPathException r7 = new com.hp.hpl.sparta.xpath.XPathException
            r7.<init>((com.hp.hpl.sparta.xpath.XPath) r5, (java.lang.Exception) r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hp.hpl.sparta.xpath.XPath.<init>(java.lang.String, java.io.Reader):void");
    }

    public String toString() {
        if (this.string_ == null) {
            this.string_ = generateString();
        }
        return this.string_;
    }

    private String generateString() {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration elements = this.steps_.elements();
        boolean z = true;
        while (elements.hasMoreElements()) {
            Step step = (Step) elements.nextElement();
            if (!z || this.absolute_) {
                stringBuffer.append('/');
                if (step.isMultiLevel()) {
                    stringBuffer.append('/');
                }
            }
            stringBuffer.append(step.toString());
            z = false;
        }
        return stringBuffer.toString();
    }

    public boolean isAbsolute() {
        return this.absolute_;
    }

    public boolean isStringValue() {
        return ((Step) this.steps_.peek()).isStringValue();
    }

    public Enumeration getSteps() {
        return this.steps_.elements();
    }

    public String getIndexingAttrName() throws XPathException {
        BooleanExpr predicate = ((Step) this.steps_.peek()).getPredicate();
        if (predicate instanceof AttrExistsExpr) {
            return ((AttrExistsExpr) predicate).getAttrName();
        }
        throw new XPathException(this, "has no indexing attribute name (must end with predicate of the form [@attrName]");
    }

    public String getIndexingAttrNameOfEquals() throws XPathException {
        BooleanExpr predicate = ((Step) this.steps_.peek()).getPredicate();
        if (predicate instanceof AttrEqualsExpr) {
            return ((AttrEqualsExpr) predicate).getAttrName();
        }
        return null;
    }

    public Object clone() {
        int size = this.steps_.size();
        Step[] stepArr = new Step[size];
        Enumeration elements = this.steps_.elements();
        for (int i = 0; i < size; i++) {
            stepArr[i] = (Step) elements.nextElement();
        }
        return new XPath(this.absolute_, stepArr);
    }

    public static XPath get(String str) throws XPathException {
        XPath xPath;
        synchronized (cache_) {
            xPath = (XPath) cache_.get(str);
            if (xPath == null) {
                xPath = new XPath(str);
                cache_.put(str, xPath);
            }
        }
        return xPath;
    }

    public static XPath get(boolean z, Step[] stepArr) {
        XPath xPath = new XPath(z, stepArr);
        String xPath2 = xPath.toString();
        synchronized (cache_) {
            XPath xPath3 = (XPath) cache_.get(xPath2);
            if (xPath3 != null) {
                return xPath3;
            }
            cache_.put(xPath2, xPath);
            return xPath;
        }
    }

    public static boolean isStringValue(String str) throws XPathException, IOException {
        return get(str).isStringValue();
    }
}
