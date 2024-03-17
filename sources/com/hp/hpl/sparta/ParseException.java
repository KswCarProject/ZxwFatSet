package com.hp.hpl.sparta;

public class ParseException extends Exception {
    private Throwable cause_;
    private int lineNumber_;

    public ParseException(String str) {
        super(str);
        this.lineNumber_ = -1;
        this.cause_ = null;
    }

    public ParseException(String str, Throwable th) {
        super(str + " " + th);
        this.lineNumber_ = -1;
        this.cause_ = null;
        this.cause_ = th;
    }

    public ParseException(String str, int i, int i2, String str2, String str3) {
        super(toMessage(str, i, i2, str2, str3));
        this.lineNumber_ = -1;
        this.cause_ = null;
        this.lineNumber_ = i;
    }

    public ParseException(ParseLog parseLog, String str, int i, int i2, String str2, String str3) {
        this(str, i, i2, str2, str3);
        parseLog.error(str3, str, i);
    }

    public ParseException(ParseCharStream parseCharStream, String str) {
        this(parseCharStream.getLog(), parseCharStream.getSystemId(), parseCharStream.getLineNumber(), parseCharStream.getLastCharRead(), parseCharStream.getHistory(), str);
    }

    public ParseException(ParseCharStream parseCharStream, char c, char c2) {
        this(parseCharStream, "got '" + c + "' instead of expected '" + c2 + "'");
    }

    public ParseException(ParseCharStream parseCharStream, char c, char[] cArr) {
        this(parseCharStream, "got '" + c + "' instead of " + toString(cArr));
    }

    public ParseException(ParseCharStream parseCharStream, char c, String str) {
        this(parseCharStream, "got '" + c + "' instead of " + str + " as expected");
    }

    public ParseException(ParseCharStream parseCharStream, String str, String str2) {
        this(parseCharStream, "got \"" + str + "\" instead of \"" + str2 + "\" as expected");
    }

    private static String toString(char[] cArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cArr[0]);
        for (int i = 1; i < cArr.length; i++) {
            stringBuffer.append("or " + cArr[i]);
        }
        return stringBuffer.toString();
    }

    public ParseException(ParseCharStream parseCharStream, String str, char[] cArr) {
        this(parseCharStream, str, new String(cArr));
    }

    public int getLineNumber() {
        return this.lineNumber_;
    }

    public Throwable getCause() {
        return this.cause_;
    }

    private static String toMessage(String str, int i, int i2, String str2, String str3) {
        return str + "(" + i + "): \n" + str2 + "\nLast character read was '" + charRepr(i2) + "'\n" + str3;
    }

    static String charRepr(int i) {
        return i == -1 ? "EOF" : "" + ((char) i);
    }
}
