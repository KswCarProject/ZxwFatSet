package com.hp.hpl.sparta.xpath;

import java.io.IOException;

public class XPathException extends Exception {
    private Throwable cause_;

    public XPathException(XPath xPath, String str) {
        super(xPath + " " + str);
        this.cause_ = null;
    }

    XPathException(XPath xPath, String str, SimpleStreamTokenizer simpleStreamTokenizer, String str2) {
        this(xPath, str + " got \"" + toString(simpleStreamTokenizer) + "\" instead of expected " + str2);
    }

    XPathException(XPath xPath, Exception exc) {
        super(xPath + " " + exc);
        this.cause_ = null;
        this.cause_ = exc;
    }

    public Throwable getCause() {
        return this.cause_;
    }

    private static String toString(SimpleStreamTokenizer simpleStreamTokenizer) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(tokenToString(simpleStreamTokenizer));
            if (simpleStreamTokenizer.ttype != -1) {
                simpleStreamTokenizer.nextToken();
                stringBuffer.append(tokenToString(simpleStreamTokenizer));
                simpleStreamTokenizer.pushBack();
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            return "(cannot get  info: " + e + ")";
        }
    }

    private static String tokenToString(SimpleStreamTokenizer simpleStreamTokenizer) {
        int i = simpleStreamTokenizer.ttype;
        if (i == -3) {
            return simpleStreamTokenizer.sval;
        }
        if (i != -2) {
            return i != -1 ? ((char) simpleStreamTokenizer.ttype) + "" : "<end of expression>";
        }
        return simpleStreamTokenizer.nval + "";
    }
}
