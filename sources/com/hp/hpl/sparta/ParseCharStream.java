package com.hp.hpl.sparta;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;

class ParseCharStream implements ParseSource {
    private static final char[] BEGIN_CDATA = "<![CDATA[".toCharArray();
    private static final char[] BEGIN_ETAG = "</".toCharArray();
    private static final char[] CHARREF_BEGIN = "&#".toCharArray();
    private static final char[] COMMENT_BEGIN = "<!--".toCharArray();
    private static final char[] COMMENT_END = "-->".toCharArray();
    private static final boolean DEBUG = true;
    private static final char[] DOCTYPE_BEGIN = "<!DOCTYPE".toCharArray();
    private static final char[] ENCODING = "encoding".toCharArray();
    private static final char[] END_CDATA = "]]>".toCharArray();
    private static final char[] END_EMPTYTAG = "/>".toCharArray();
    private static final char[] ENTITY_BEGIN = "<!ENTITY".toCharArray();
    public static final int HISTORY_LENGTH = 100;
    private static final boolean H_DEBUG = false;
    private static final boolean[] IS_NAME_CHAR = new boolean[128];
    private static final char[] MARKUPDECL_BEGIN = "<!".toCharArray();
    private static final int MAX_COMMON_CHAR = 128;
    private static final char[] NAME_PUNCT_CHARS = {'.', '-', '_', ':'};
    private static final char[] NDATA = "NDATA".toCharArray();
    private static final char[] PI_BEGIN = "<?".toCharArray();
    private static final char[] PUBLIC = "PUBLIC".toCharArray();
    private static final char[] QU_END = "?>".toCharArray();
    private static final char[] SYSTEM = "SYSTEM".toCharArray();
    private static final int TMP_BUF_SIZE = 255;
    private static final char[] VERSION = "version".toCharArray();
    private static final char[] VERSIONNUM_PUNC_CHARS = {'_', '.', ':', '-'};
    private static final char[] XML_BEGIN = "<?xml".toCharArray();
    private final int CBUF_SIZE;
    private final char[] cbuf_;
    private int ch_;
    private int curPos_;
    private String docTypeName_;
    private final String encoding_;
    private int endPos_;
    private final Hashtable entities_;
    private boolean eos_;
    private final ParseHandler handler_;
    private final CharCircBuffer history_;
    private boolean isExternalDtd_;
    private int lineNumber_;
    private final ParseLog log_;
    private final Hashtable pes_;
    private final Reader reader_;
    private String systemId_;
    private final char[] tmpBuf_;

    private static boolean isExtender(char c) {
        if (c == 183 || c == 903 || c == 1600 || c == 3654 || c == 3782 || c == 12293 || c == 720 || c == 721 || c == 12445 || c == 12446) {
            return DEBUG;
        }
        switch (c) {
            case 12337:
            case 12338:
            case 12339:
            case 12340:
            case 12341:
                return DEBUG;
            default:
                switch (c) {
                    case 12540:
                    case 12541:
                    case 12542:
                        return DEBUG;
                    default:
                        return false;
                }
        }
    }

    /* access modifiers changed from: package-private */
    public final String getHistory() {
        return "";
    }

    public ParseCharStream(String str, char[] cArr, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, EncodingMismatchException, IOException {
        this(str, (Reader) null, cArr, parseLog, str2, parseHandler);
    }

    public ParseCharStream(String str, Reader reader, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, EncodingMismatchException, IOException {
        this(str, reader, (char[]) null, parseLog, str2, parseHandler);
    }

    public ParseCharStream(String str, Reader reader, char[] cArr, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, EncodingMismatchException, IOException {
        String str3;
        this.docTypeName_ = null;
        Hashtable hashtable = new Hashtable();
        this.entities_ = hashtable;
        this.pes_ = new Hashtable();
        this.ch_ = -2;
        this.isExternalDtd_ = false;
        this.CBUF_SIZE = 1024;
        this.curPos_ = 0;
        this.endPos_ = 0;
        this.eos_ = false;
        this.tmpBuf_ = new char[255];
        this.lineNumber_ = -1;
        this.lineNumber_ = 1;
        this.history_ = null;
        parseLog = parseLog == null ? DEFAULT_LOG : parseLog;
        this.log_ = parseLog;
        if (str2 == null) {
            str3 = null;
        } else {
            str3 = str2.toLowerCase();
        }
        this.encoding_ = str3;
        hashtable.put("lt", "<");
        hashtable.put("gt", ">");
        hashtable.put("amp", "&");
        hashtable.put("apos", "'");
        hashtable.put("quot", "\"");
        if (cArr != null) {
            this.cbuf_ = cArr;
            this.curPos_ = 0;
            this.endPos_ = cArr.length;
            this.eos_ = DEBUG;
            this.reader_ = null;
        } else {
            this.reader_ = reader;
            this.cbuf_ = new char[1024];
            fillBuf();
        }
        this.systemId_ = str;
        this.handler_ = parseHandler;
        parseHandler.setParseSource(this);
        readProlog();
        parseHandler.startDocument();
        Element readElement = readElement();
        String str4 = this.docTypeName_;
        if (str4 != null && !str4.equals(readElement.getTagName())) {
            parseLog.warning("DOCTYPE name \"" + this.docTypeName_ + "\" not same as tag name, \"" + readElement.getTagName() + "\" of root element", this.systemId_, getLineNumber());
        }
        while (isMisc()) {
            readMisc();
        }
        Reader reader2 = this.reader_;
        if (reader2 != null) {
            reader2.close();
        }
        this.handler_.endDocument();
    }

    public String toString() {
        return this.systemId_;
    }

    public String getSystemId() {
        return this.systemId_;
    }

    public int getLineNumber() {
        return this.lineNumber_;
    }

    /* access modifiers changed from: package-private */
    public int getLastCharRead() {
        return this.ch_;
    }

    private int fillBuf() throws IOException {
        if (this.eos_) {
            return -1;
        }
        int i = this.endPos_;
        char[] cArr = this.cbuf_;
        if (i == cArr.length) {
            this.endPos_ = 0;
            this.curPos_ = 0;
        }
        Reader reader = this.reader_;
        int i2 = this.endPos_;
        int read = reader.read(cArr, i2, cArr.length - i2);
        if (read <= 0) {
            this.eos_ = DEBUG;
            return -1;
        }
        this.endPos_ += read;
        return read;
    }

    private int fillBuf(int i) throws IOException {
        int i2;
        int i3;
        if (this.eos_) {
            return -1;
        }
        int i4 = 0;
        if (this.cbuf_.length - this.curPos_ < i) {
            int i5 = 0;
            while (true) {
                i2 = this.curPos_;
                int i6 = i2 + i5;
                i3 = this.endPos_;
                if (i6 >= i3) {
                    break;
                }
                char[] cArr = this.cbuf_;
                cArr[i5] = cArr[i2 + i5];
                i5++;
            }
            int i7 = i3 - i2;
            this.endPos_ = i7;
            this.curPos_ = 0;
            i4 = i7;
        }
        int fillBuf = fillBuf();
        if (fillBuf != -1) {
            return i4 + fillBuf;
        }
        if (i4 == 0) {
            return -1;
        }
        return i4;
    }

    private final char readChar() throws ParseException, IOException {
        if (this.curPos_ < this.endPos_ || fillBuf() != -1) {
            char[] cArr = this.cbuf_;
            int i = this.curPos_;
            if (cArr[i] == 10) {
                this.lineNumber_++;
            }
            this.curPos_ = i + 1;
            return cArr[i];
        }
        throw new ParseException(this, "unexpected end of expression.");
    }

    private final char peekChar() throws ParseException, IOException {
        if (this.curPos_ < this.endPos_ || fillBuf() != -1) {
            return this.cbuf_[this.curPos_];
        }
        throw new ParseException(this, "unexpected end of expression.");
    }

    private final void readChar(char c) throws ParseException, IOException {
        char readChar = readChar();
        if (readChar != c) {
            throw new ParseException(this, readChar, c);
        }
    }

    private final boolean isChar(char c) throws ParseException, IOException {
        if (this.curPos_ >= this.endPos_ && fillBuf() == -1) {
            throw new ParseException(this, "unexpected end of expression.");
        } else if (this.cbuf_[this.curPos_] == c) {
            return DEBUG;
        } else {
            return false;
        }
    }

    private final char readChar(char c, char c2) throws ParseException, IOException {
        char readChar = readChar();
        if (readChar == c || readChar == c2) {
            return readChar;
        }
        throw new ParseException(this, readChar, new char[]{c, c2});
    }

    private final char readChar(char c, char c2, char c3, char c4) throws ParseException, IOException {
        char readChar = readChar();
        if (readChar == c || readChar == c2 || readChar == c3 || readChar == c4) {
            return readChar;
        }
        throw new ParseException(this, readChar, new char[]{c, c2, c3, c4});
    }

    private final boolean isChar(char c, char c2) throws ParseException, IOException {
        if (this.curPos_ >= this.endPos_ && fillBuf() == -1) {
            return false;
        }
        char c3 = this.cbuf_[this.curPos_];
        if (c3 == c || c3 == c2) {
            return DEBUG;
        }
        return false;
    }

    private final boolean isChar(char c, char c2, char c3, char c4) throws ParseException, IOException {
        if (this.curPos_ >= this.endPos_ && fillBuf() == -1) {
            return false;
        }
        char c5 = this.cbuf_[this.curPos_];
        if (c5 == c || c5 == c2 || c5 == c3 || c5 == c4) {
            return DEBUG;
        }
        return false;
    }

    private static final boolean isIn(char c, char[] cArr) {
        for (char c2 : cArr) {
            if (c == c2) {
                return DEBUG;
            }
        }
        return false;
    }

    private final void readS() throws ParseException, IOException {
        readChar(' ', 9, 13, 10);
        while (isChar(' ', 9, 13, 10)) {
            readChar();
        }
    }

    private final boolean isS() throws ParseException, IOException {
        return isChar(' ', 9, 13, 10);
    }

    static {
        for (char c = 0; c < 128; c = (char) (c + 1)) {
            IS_NAME_CHAR[c] = isNameChar(c);
        }
    }

    private boolean isNameChar() throws ParseException, IOException {
        char peekChar = peekChar();
        return peekChar < 128 ? IS_NAME_CHAR[peekChar] : isNameChar(peekChar);
    }

    private static boolean isLetter(char c) {
        if ("abcdefghijklmnopqrstuvwxyz".indexOf(Character.toLowerCase(c)) != -1) {
            return DEBUG;
        }
        return false;
    }

    private static boolean isNameChar(char c) {
        if (Character.isDigit(c) || isLetter(c) || isIn(c, NAME_PUNCT_CHARS) || isExtender(c)) {
            return DEBUG;
        }
        return false;
    }

    private final String readName() throws ParseException, IOException {
        this.tmpBuf_[0] = readNameStartChar();
        int i = 1;
        StringBuffer stringBuffer = null;
        while (isNameChar()) {
            if (i >= 255) {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(i);
                    stringBuffer.append(this.tmpBuf_, 0, i);
                } else {
                    stringBuffer.append(this.tmpBuf_, 0, i);
                }
                i = 0;
            }
            this.tmpBuf_[i] = readChar();
            i++;
        }
        if (stringBuffer == null) {
            return Sparta.intern(new String(this.tmpBuf_, 0, i));
        }
        stringBuffer.append(this.tmpBuf_, 0, i);
        return stringBuffer.toString();
    }

    private char readNameStartChar() throws ParseException, IOException {
        char readChar = readChar();
        if (isLetter(readChar) || readChar == '_' || readChar == ':') {
            return readChar;
        }
        throw new ParseException(this, readChar, "letter, underscore, colon");
    }

    private final String readEntityValue() throws ParseException, IOException {
        char readChar = readChar('\'', '\"');
        StringBuffer stringBuffer = new StringBuffer();
        while (!isChar(readChar)) {
            if (isPeReference()) {
                stringBuffer.append(readPeReference());
            } else if (isReference()) {
                stringBuffer.append(readReference());
            } else {
                stringBuffer.append(readChar());
            }
        }
        readChar(readChar);
        return stringBuffer.toString();
    }

    private final boolean isEntityValue() throws ParseException, IOException {
        return isChar('\'', '\"');
    }

    private final void readSystemLiteral() throws ParseException, IOException {
        char readChar = readChar();
        while (peekChar() != readChar) {
            readChar();
        }
        readChar(readChar);
    }

    private final void readPubidLiteral() throws ParseException, IOException {
        readSystemLiteral();
    }

    private boolean isMisc() throws ParseException, IOException {
        if (isComment() || isPi() || isS()) {
            return DEBUG;
        }
        return false;
    }

    private void readMisc() throws ParseException, IOException {
        if (isComment()) {
            readComment();
        } else if (isPi()) {
            readPi();
        } else if (isS()) {
            readS();
        } else {
            throw new ParseException(this, "expecting comment or processing instruction or space");
        }
    }

    private final void readComment() throws ParseException, IOException {
        readSymbol(COMMENT_BEGIN);
        while (true) {
            char[] cArr = COMMENT_END;
            if (!isSymbol(cArr)) {
                readChar();
            } else {
                readSymbol(cArr);
                return;
            }
        }
    }

    private final boolean isComment() throws ParseException, IOException {
        return isSymbol(COMMENT_BEGIN);
    }

    private final void readPi() throws ParseException, IOException {
        readSymbol(PI_BEGIN);
        while (true) {
            char[] cArr = QU_END;
            if (!isSymbol(cArr)) {
                readChar();
            } else {
                readSymbol(cArr);
                return;
            }
        }
    }

    private final boolean isPi() throws ParseException, IOException {
        return isSymbol(PI_BEGIN);
    }

    private void readProlog() throws ParseException, EncodingMismatchException, IOException {
        if (isXmlDecl()) {
            readXmlDecl();
        }
        while (isMisc()) {
            readMisc();
        }
        if (isDocTypeDecl()) {
            readDocTypeDecl();
            while (isMisc()) {
                readMisc();
            }
        }
    }

    private boolean isDocTypeDecl() throws ParseException, IOException {
        return isSymbol(DOCTYPE_BEGIN);
    }

    private void readXmlDecl() throws ParseException, EncodingMismatchException, IOException {
        readSymbol(XML_BEGIN);
        readVersionInfo();
        if (isS()) {
            readS();
        }
        if (isEncodingDecl()) {
            String readEncodingDecl = readEncodingDecl();
            if (this.encoding_ != null && !readEncodingDecl.toLowerCase().equals(this.encoding_)) {
                throw new EncodingMismatchException(this.systemId_, readEncodingDecl, this.encoding_);
            }
        }
        while (true) {
            char[] cArr = QU_END;
            if (!isSymbol(cArr)) {
                readChar();
            } else {
                readSymbol(cArr);
                return;
            }
        }
    }

    private boolean isXmlDecl() throws ParseException, IOException {
        return isSymbol(XML_BEGIN);
    }

    private boolean isEncodingDecl() throws ParseException, IOException {
        return isSymbol(ENCODING);
    }

    private String readEncodingDecl() throws ParseException, IOException {
        readSymbol(ENCODING);
        readEq();
        char readChar = readChar('\'', '\"');
        StringBuffer stringBuffer = new StringBuffer();
        while (!isChar(readChar)) {
            stringBuffer.append(readChar());
        }
        readChar(readChar);
        return stringBuffer.toString();
    }

    private void readVersionInfo() throws ParseException, IOException {
        readS();
        readSymbol(VERSION);
        readEq();
        char readChar = readChar('\'', '\"');
        readVersionNum();
        readChar(readChar);
    }

    private final void readEq() throws ParseException, IOException {
        if (isS()) {
            readS();
        }
        readChar('=');
        if (isS()) {
            readS();
        }
    }

    private boolean isVersionNumChar() throws ParseException, IOException {
        char peekChar = peekChar();
        if (Character.isDigit(peekChar) || (('a' <= peekChar && peekChar <= 'z') || (('Z' <= peekChar && peekChar <= 'Z') || isIn(peekChar, VERSIONNUM_PUNC_CHARS)))) {
            return DEBUG;
        }
        return false;
    }

    private void readVersionNum() throws ParseException, IOException {
        readChar();
        while (isVersionNumChar()) {
            readChar();
        }
    }

    private void readDocTypeDecl() throws ParseException, IOException {
        readSymbol(DOCTYPE_BEGIN);
        readS();
        this.docTypeName_ = readName();
        if (isS()) {
            readS();
            if (!isChar('>') && !isChar('[')) {
                this.isExternalDtd_ = DEBUG;
                readExternalId();
                if (isS()) {
                    readS();
                }
            }
        }
        if (isChar('[')) {
            readChar();
            while (!isChar(']')) {
                if (isDeclSep()) {
                    readDeclSep();
                } else {
                    readMarkupDecl();
                }
            }
            readChar(']');
            if (isS()) {
                readS();
            }
        }
        readChar('>');
    }

    private void readDeclSep() throws ParseException, IOException {
        if (isPeReference()) {
            readPeReference();
        } else {
            readS();
        }
    }

    private boolean isDeclSep() throws ParseException, IOException {
        if (isPeReference() || isS()) {
            return DEBUG;
        }
        return false;
    }

    private void readMarkupDecl() throws ParseException, IOException {
        if (isPi()) {
            readPi();
        } else if (isComment()) {
            readComment();
        } else if (isEntityDecl()) {
            readEntityDecl();
        } else if (isSymbol(MARKUPDECL_BEGIN)) {
            while (!isChar('>')) {
                if (isChar('\'', '\"')) {
                    char readChar = readChar();
                    while (!isChar(readChar)) {
                        readChar();
                    }
                    readChar(readChar);
                } else {
                    readChar();
                }
            }
            readChar('>');
        } else {
            throw new ParseException(this, "expecting processing instruction, comment, or \"<!\"");
        }
    }

    private char readCharRef() throws ParseException, IOException {
        int i;
        readSymbol(CHARREF_BEGIN);
        if (isChar('x')) {
            readChar();
            i = 16;
        } else {
            i = 10;
        }
        int i2 = 0;
        while (!isChar(';')) {
            int i3 = i2 + 1;
            this.tmpBuf_[i2] = readChar();
            if (i3 >= 255) {
                this.log_.warning("Tmp buffer overflow on readCharRef", this.systemId_, getLineNumber());
                return ' ';
            }
            i2 = i3;
        }
        readChar(';');
        String str = new String(this.tmpBuf_, 0, i2);
        try {
            return (char) Integer.parseInt(str, i);
        } catch (NumberFormatException unused) {
            this.log_.warning("\"" + str + "\" is not a valid " + (i == 16 ? "hexadecimal" : "decimal") + " number", this.systemId_, getLineNumber());
            return ' ';
        }
    }

    private final char[] readReference() throws ParseException, IOException {
        if (!isSymbol(CHARREF_BEGIN)) {
            return readEntityRef().toCharArray();
        }
        return new char[]{readCharRef()};
    }

    private final boolean isReference() throws ParseException, IOException {
        return isChar('&');
    }

    private String readEntityRef() throws ParseException, IOException {
        readChar('&');
        String readName = readName();
        String str = (String) this.entities_.get(readName);
        if (str == null) {
            if (this.isExternalDtd_) {
                this.log_.warning("&" + readName + "; not found -- possibly defined in external DTD)", this.systemId_, getLineNumber());
            } else {
                this.log_.warning("No declaration of &" + readName + ";", this.systemId_, getLineNumber());
            }
            str = "";
        }
        readChar(';');
        return str;
    }

    private String readPeReference() throws ParseException, IOException {
        readChar('%');
        String readName = readName();
        String str = (String) this.pes_.get(readName);
        if (str == null) {
            this.log_.warning("No declaration of %" + readName + ";", this.systemId_, getLineNumber());
            str = "";
        }
        readChar(';');
        return str;
    }

    private boolean isPeReference() throws ParseException, IOException {
        return isChar('%');
    }

    private void readEntityDecl() throws ParseException, IOException {
        String str;
        String str2;
        readSymbol(ENTITY_BEGIN);
        readS();
        if (isChar('%')) {
            readChar('%');
            readS();
            String readName = readName();
            readS();
            if (isEntityValue()) {
                str2 = readEntityValue();
            } else {
                str2 = readExternalId();
            }
            this.pes_.put(readName, str2);
        } else {
            String readName2 = readName();
            readS();
            if (isEntityValue()) {
                str = readEntityValue();
            } else if (isExternalId()) {
                str = readExternalId();
                if (isS()) {
                    readS();
                }
                char[] cArr = NDATA;
                if (isSymbol(cArr)) {
                    readSymbol(cArr);
                    readS();
                    readName();
                }
            } else {
                throw new ParseException(this, "expecting double-quote, \"PUBLIC\" or \"SYSTEM\" while reading entity declaration");
            }
            this.entities_.put(readName2, str);
        }
        if (isS()) {
            readS();
        }
        readChar('>');
    }

    private boolean isEntityDecl() throws ParseException, IOException {
        return isSymbol(ENTITY_BEGIN);
    }

    private String readExternalId() throws ParseException, IOException {
        char[] cArr = SYSTEM;
        if (isSymbol(cArr)) {
            readSymbol(cArr);
        } else {
            char[] cArr2 = PUBLIC;
            if (isSymbol(cArr2)) {
                readSymbol(cArr2);
                readS();
                readPubidLiteral();
            } else {
                throw new ParseException(this, "expecting \"SYSTEM\" or \"PUBLIC\" while reading external ID");
            }
        }
        readS();
        readSystemLiteral();
        return "(WARNING: external ID not read)";
    }

    private boolean isExternalId() throws ParseException, IOException {
        if (isSymbol(SYSTEM) || isSymbol(PUBLIC)) {
            return DEBUG;
        }
        return false;
    }

    private final void readSymbol(char[] cArr) throws ParseException, IOException {
        int length = cArr.length;
        if (this.endPos_ - this.curPos_ >= length || fillBuf(length) > 0) {
            char[] cArr2 = this.cbuf_;
            int i = this.endPos_;
            this.ch_ = cArr2[i - 1];
            if (i - this.curPos_ >= length) {
                int i2 = 0;
                while (i2 < length) {
                    if (this.cbuf_[this.curPos_ + i2] == cArr[i2]) {
                        i2++;
                    } else {
                        throw new ParseException(this, new String(this.cbuf_, this.curPos_, length), cArr);
                    }
                }
                this.curPos_ += length;
                return;
            }
            throw new ParseException(this, "end of XML file", cArr);
        }
        this.ch_ = -1;
        throw new ParseException(this, "end of XML file", cArr);
    }

    private final boolean isSymbol(char[] cArr) throws ParseException, IOException {
        int length = cArr.length;
        if (this.endPos_ - this.curPos_ >= length || fillBuf(length) > 0) {
            char[] cArr2 = this.cbuf_;
            int i = this.endPos_;
            this.ch_ = cArr2[i - 1];
            if (i - this.curPos_ < length) {
                return false;
            }
            for (int i2 = 0; i2 < length; i2++) {
                if (this.cbuf_[this.curPos_ + i2] != cArr[i2]) {
                    return false;
                }
            }
            return DEBUG;
        }
        this.ch_ = -1;
        return false;
    }

    private String readAttValue() throws ParseException, IOException {
        char readChar = readChar('\'', '\"');
        StringBuffer stringBuffer = new StringBuffer();
        while (!isChar(readChar)) {
            if (isReference()) {
                stringBuffer.append(readReference());
            } else {
                stringBuffer.append(readChar());
            }
        }
        readChar(readChar);
        return stringBuffer.toString();
    }

    private void readPossibleCharData() throws ParseException, IOException {
        int i;
        loop0:
        while (true) {
            i = 0;
            while (!isChar('<') && !isChar('&') && !isSymbol(END_CDATA)) {
                this.tmpBuf_[i] = readChar();
                if (this.tmpBuf_[i] == 13 && peekChar() == 10) {
                    this.tmpBuf_[i] = readChar();
                }
                i++;
                if (i == 255) {
                    this.handler_.characters(this.tmpBuf_, 0, 255);
                }
            }
        }
        if (i > 0) {
            this.handler_.characters(this.tmpBuf_, 0, i);
        }
    }

    private void readCdSect() throws ParseException, IOException {
        char[] cArr;
        readSymbol(BEGIN_CDATA);
        StringBuffer stringBuffer = null;
        int i = 0;
        while (true) {
            cArr = END_CDATA;
            if (isSymbol(cArr)) {
                break;
            }
            if (i >= 255) {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(i);
                    stringBuffer.append(this.tmpBuf_, 0, i);
                } else {
                    stringBuffer.append(this.tmpBuf_, 0, i);
                }
                i = 0;
            }
            this.tmpBuf_[i] = readChar();
            i++;
        }
        readSymbol(cArr);
        if (stringBuffer != null) {
            stringBuffer.append(this.tmpBuf_, 0, i);
            char[] charArray = stringBuffer.toString().toCharArray();
            this.handler_.characters(charArray, 0, charArray.length);
            return;
        }
        this.handler_.characters(this.tmpBuf_, 0, i);
    }

    private boolean isCdSect() throws ParseException, IOException {
        return isSymbol(BEGIN_CDATA);
    }

    private final Element readElement() throws ParseException, IOException {
        Element element = new Element();
        boolean readEmptyElementTagOrSTag = readEmptyElementTagOrSTag(element);
        this.handler_.startElement(element);
        if (readEmptyElementTagOrSTag) {
            readContent();
            readETag(element);
        }
        this.handler_.endElement(element);
        return element;
    }

    /* access modifiers changed from: package-private */
    public ParseLog getLog() {
        return this.log_;
    }

    private boolean readEmptyElementTagOrSTag(Element element) throws ParseException, IOException {
        readChar('<');
        element.setTagName(readName());
        while (isS()) {
            readS();
            if (!isChar('/', '>')) {
                readAttribute(element);
            }
        }
        if (isS()) {
            readS();
        }
        boolean isChar = isChar('>');
        if (isChar) {
            readChar('>');
        } else {
            readSymbol(END_EMPTYTAG);
        }
        return isChar;
    }

    private void readAttribute(Element element) throws ParseException, IOException {
        String readName = readName();
        readEq();
        String readAttValue = readAttValue();
        if (element.getAttribute(readName) != null) {
            this.log_.warning("Element " + this + " contains attribute " + readName + "more than once", this.systemId_, getLineNumber());
        }
        element.setAttribute(readName, readAttValue);
    }

    private void readETag(Element element) throws ParseException, IOException {
        readSymbol(BEGIN_ETAG);
        String readName = readName();
        if (!readName.equals(element.getTagName())) {
            this.log_.warning("end tag (" + readName + ") does not match begin tag (" + element.getTagName() + ")", this.systemId_, getLineNumber());
        }
        if (isS()) {
            readS();
        }
        readChar('>');
    }

    private boolean isETag() throws ParseException, IOException {
        return isSymbol(BEGIN_ETAG);
    }

    private void readContent() throws ParseException, IOException {
        readPossibleCharData();
        boolean z = DEBUG;
        while (z) {
            if (!isETag()) {
                if (isReference()) {
                    char[] readReference = readReference();
                    this.handler_.characters(readReference, 0, readReference.length);
                } else if (isCdSect()) {
                    readCdSect();
                } else if (isPi()) {
                    readPi();
                } else if (isComment()) {
                    readComment();
                } else if (isChar('<')) {
                    readElement();
                }
                readPossibleCharData();
            }
            z = false;
            readPossibleCharData();
        }
    }
}
