package com.hp.hpl.sparta.xpath;

import java.io.IOException;
import java.io.Reader;

public class SimpleStreamTokenizer {
    private static final int QUOTE = -6;
    public static final int TT_EOF = -1;
    public static final int TT_NUMBER = -2;
    public static final int TT_WORD = -3;
    private static final int WHITESPACE = -5;
    private final StringBuffer buf_ = new StringBuffer();
    private final int[] charType_ = new int[256];
    private char inQuote_;
    private int nextType_;
    public int nval = Integer.MIN_VALUE;
    private boolean pushedBack_;
    private final Reader reader_;
    public String sval = "";
    public int ttype = Integer.MIN_VALUE;

    public String toString() {
        int i = this.ttype;
        if (i != -3) {
            if (i == -2) {
                return Integer.toString(this.nval);
            }
            if (i == -1) {
                return "(EOF)";
            }
            if (i != 34) {
                if (i != 39) {
                    return "'" + ((char) this.ttype) + "'";
                }
                return "'" + this.sval + "'";
            }
        }
        return "\"" + this.sval + "\"";
    }

    public SimpleStreamTokenizer(Reader reader) throws IOException {
        int i = 0;
        this.pushedBack_ = false;
        this.inQuote_ = 0;
        this.reader_ = reader;
        while (true) {
            int[] iArr = this.charType_;
            if (i < iArr.length) {
                if ((65 <= i && i <= 90) || ((97 <= i && i <= 122) || i == 45)) {
                    iArr[i] = -3;
                } else if (48 <= i && i <= 57) {
                    iArr[i] = -2;
                } else if (i < 0 || i > 32) {
                    iArr[i] = i;
                } else {
                    iArr[i] = WHITESPACE;
                }
                i = (char) (i + 1);
            } else {
                nextToken();
                return;
            }
        }
    }

    public void ordinaryChar(char c) {
        this.charType_[c] = c;
    }

    public void wordChars(char c, char c2) {
        while (c <= c2) {
            this.charType_[c] = -3;
            c = (char) (c + 1);
        }
    }

    public int nextToken() throws IOException {
        int read;
        char c;
        char c2;
        boolean z;
        boolean z2;
        int i;
        if (this.pushedBack_) {
            this.pushedBack_ = false;
            return this.ttype;
        }
        this.ttype = this.nextType_;
        do {
            boolean z3 = false;
            do {
                read = this.reader_.read();
                if (read != -1) {
                    c = this.charType_[read];
                } else if (this.inQuote_ == 0) {
                    c = -1;
                } else {
                    throw new IOException("Unterminated quote");
                }
                c2 = this.inQuote_;
                z = c2 == 0 && c == WHITESPACE;
                if (z3 || z) {
                    z3 = true;
                    continue;
                } else {
                    z3 = false;
                    continue;
                }
            } while (z);
            if (c == 39 || c == 34) {
                if (c2 == 0) {
                    this.inQuote_ = (char) c;
                } else if (c2 == c) {
                    this.inQuote_ = 0;
                }
            }
            char c3 = this.inQuote_;
            if (c3 != 0) {
                c = c3;
            }
            z2 = z3 || !(((i = this.ttype) < -1 || i == 39 || i == 34) && i == c);
            if (z2) {
                int i2 = this.ttype;
                if (i2 == -3) {
                    this.sval = this.buf_.toString();
                    this.buf_.setLength(0);
                } else if (i2 == -2) {
                    this.nval = Integer.parseInt(this.buf_.toString());
                    this.buf_.setLength(0);
                } else if (i2 == 34 || i2 == 39) {
                    this.sval = this.buf_.toString().substring(1, this.buf_.length() - 1);
                    this.buf_.setLength(0);
                }
                if (c != WHITESPACE) {
                    this.nextType_ = c == QUOTE ? read : c;
                }
            }
            if (c == -3 || c == -2 || c == 34 || c == 39) {
                this.buf_.append((char) read);
                continue;
            }
        } while (!z2);
        return this.ttype;
    }

    public void pushBack() {
        this.pushedBack_ = true;
    }
}
