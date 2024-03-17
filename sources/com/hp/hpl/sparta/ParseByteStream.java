package com.hp.hpl.sparta;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

class ParseByteStream implements ParseSource {
    private ParseCharStream parseSource_;

    public ParseByteStream(String str, InputStream inputStream, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, IOException {
        String str3 = str;
        InputStream inputStream2 = inputStream;
        ParseLog parseLog2 = parseLog == null ? DEFAULT_LOG : parseLog;
        if (inputStream.markSupported()) {
            inputStream2.mark(MAXLOOKAHEAD);
            byte[] bArr = new byte[4];
            String guessEncoding = str2 == null ? guessEncoding(str3, bArr, inputStream2.read(bArr), parseLog2) : str2;
            try {
                inputStream.reset();
                try {
                    this.parseSource_ = new ParseCharStream(str, (Reader) new InputStreamReader(inputStream2, fixEncoding(guessEncoding)), parseLog2, guessEncoding, parseHandler);
                } catch (IOException unused) {
                    parseLog2.note("Problem reading with assumed encoding of " + guessEncoding + " so restarting with " + "euc-jp", str3, 1);
                    inputStream.reset();
                    this.parseSource_ = new ParseCharStream(str, (Reader) new InputStreamReader(inputStream2, fixEncoding("euc-jp")), parseLog2, (String) null, parseHandler);
                }
            } catch (UnsupportedEncodingException unused2) {
                throw new ParseException(parseLog2, str, 1, 0, "euc-jp", "\"" + "euc-jp" + "\" is not a supported encoding");
            } catch (EncodingMismatchException e) {
                String declaredEncoding = e.getDeclaredEncoding();
                parseLog2.note("Encoding declaration of " + declaredEncoding + " is different that assumed " + guessEncoding + " so restarting the parsing with the new encoding", str3, 1);
                inputStream.reset();
                try {
                    this.parseSource_ = new ParseCharStream(str, (Reader) new InputStreamReader(inputStream2, fixEncoding(declaredEncoding)), parseLog2, (String) null, parseHandler);
                } catch (UnsupportedEncodingException unused3) {
                    throw new ParseException(parseLog2, str, 1, 0, declaredEncoding, "\"" + declaredEncoding + "\" is not a supported encoding");
                }
            }
        } else {
            throw new Error("Precondition violation: the InputStream passed to ParseByteStream must support mark");
        }
    }

    public String toString() {
        return this.parseSource_.toString();
    }

    public String getSystemId() {
        return this.parseSource_.getSystemId();
    }

    public int getLineNumber() {
        return this.parseSource_.getLineNumber();
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00bb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String guessEncoding(java.lang.String r5, byte[] r6, int r7, com.hp.hpl.sparta.ParseLog r8) throws java.io.IOException {
        /*
            r0 = 0
            r1 = 1
            java.lang.String r2 = "UTF-8"
            r3 = 4
            if (r7 == r3) goto L_0x0030
            if (r7 > 0) goto L_0x000c
            java.lang.String r7 = "no characters in input"
            goto L_0x002a
        L_0x000c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "less than 4 characters in input: \""
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = new java.lang.String
            r4.<init>(r6, r0, r7)
            java.lang.StringBuilder r7 = r3.append(r4)
            java.lang.String r3 = "\""
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.String r7 = r7.toString()
        L_0x002a:
            r8.error(r7, r5, r1)
        L_0x002d:
            r7 = r2
            goto L_0x00b5
        L_0x0030:
            r7 = 65279(0xfeff, float:9.1475E-41)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 != 0) goto L_0x00b3
            r7 = -131072(0xfffffffffffe0000, float:NaN)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 != 0) goto L_0x00b3
            r7 = 65534(0xfffe, float:9.1833E-41)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 != 0) goto L_0x00b3
            r7 = -16842752(0xfffffffffeff0000, float:-1.6947657E38)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 != 0) goto L_0x00b3
            r7 = 60
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 != 0) goto L_0x00b3
            r7 = 1006632960(0x3c000000, float:0.0078125)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 != 0) goto L_0x00b3
            r7 = 15360(0x3c00, float:2.1524E-41)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 != 0) goto L_0x00b3
            r7 = 3932160(0x3c0000, float:5.51013E-39)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 == 0) goto L_0x0073
            goto L_0x00b3
        L_0x0073:
            r7 = 3932223(0x3c003f, float:5.510218E-39)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 == 0) goto L_0x007f
            java.lang.String r7 = "UTF-16BE"
            goto L_0x00b5
        L_0x007f:
            r7 = 1006649088(0x3c003f00, float:0.00782752)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 == 0) goto L_0x008b
            java.lang.String r7 = "UTF-16LE"
            goto L_0x00b5
        L_0x008b:
            r7 = 1010792557(0x3c3f786d, float:0.011686427)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 == 0) goto L_0x0095
            goto L_0x002d
        L_0x0095:
            r7 = 1282385812(0x4c6fa794, float:6.2824016E7)
            boolean r7 = equals((byte[]) r6, (int) r7)
            if (r7 == 0) goto L_0x00a1
            java.lang.String r7 = "EBCDIC"
            goto L_0x00b5
        L_0x00a1:
            r7 = -2
            boolean r7 = equals((byte[]) r6, (short) r7)
            if (r7 != 0) goto L_0x00b0
            r7 = -257(0xfffffffffffffeff, float:NaN)
            boolean r7 = equals((byte[]) r6, (short) r7)
            if (r7 == 0) goto L_0x002d
        L_0x00b0:
            java.lang.String r7 = "UTF-16"
            goto L_0x00b5
        L_0x00b3:
            java.lang.String r7 = "UCS-4"
        L_0x00b5:
            boolean r2 = r7.equals(r2)
            if (r2 != 0) goto L_0x010f
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "From start "
            java.lang.StringBuilder r2 = r2.append(r3)
            byte r0 = r6[r0]
            java.lang.String r0 = hex(r0)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r2 = " "
            java.lang.StringBuilder r0 = r0.append(r2)
            byte r3 = r6[r1]
            java.lang.String r3 = hex(r3)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r2)
            r3 = 2
            byte r3 = r6[r3]
            java.lang.String r3 = hex(r3)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r2)
            r2 = 3
            byte r6 = r6[r2]
            java.lang.String r6 = hex(r6)
            java.lang.StringBuilder r6 = r0.append(r6)
            java.lang.String r0 = " deduced encoding = "
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r8.note(r6, r5, r1)
        L_0x010f:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hp.hpl.sparta.ParseByteStream.guessEncoding(java.lang.String, byte[], int, com.hp.hpl.sparta.ParseLog):java.lang.String");
    }

    private static String hex(byte b) {
        String hexString = Integer.toHexString(b);
        int length = hexString.length();
        if (length != 1) {
            return length != 2 ? hexString.substring(hexString.length() - 2) : hexString;
        }
        return "0" + hexString;
    }

    private static boolean equals(byte[] bArr, int i) {
        return bArr[0] == ((byte) (i >>> 24)) && bArr[1] == ((byte) ((i >>> 16) & 255)) && bArr[2] == ((byte) ((i >>> 8) & 255)) && bArr[3] == ((byte) (i & 255));
    }

    private static boolean equals(byte[] bArr, short s) {
        return bArr[0] == ((byte) (s >>> 8)) && bArr[1] == ((byte) (s & 255));
    }

    private static String fixEncoding(String str) {
        return str.toLowerCase().equals("utf8") ? "UTF-8" : str;
    }
}
