package net.sourceforge.pinyin4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

class ChineseToPinyinResource {
    private Properties unicodeToHanyuPinyinTable;

    private void setUnicodeToHanyuPinyinTable(Properties properties) {
        this.unicodeToHanyuPinyinTable = properties;
    }

    private Properties getUnicodeToHanyuPinyinTable() {
        return this.unicodeToHanyuPinyinTable;
    }

    private ChineseToPinyinResource() {
        this.unicodeToHanyuPinyinTable = null;
        initializeResource();
    }

    private void initializeResource() {
        try {
            setUnicodeToHanyuPinyinTable(new Properties());
            getUnicodeToHanyuPinyinTable().load(ResourceHelper.getResourceInputStream("/pinyindb/unicode_to_hanyu_pinyin.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public String[] getHanyuPinyinStringArray(char c) {
        String hanyuPinyinRecordFromChar = getHanyuPinyinRecordFromChar(c);
        if (hanyuPinyinRecordFromChar == null) {
            return null;
        }
        return hanyuPinyinRecordFromChar.substring(hanyuPinyinRecordFromChar.indexOf("(") + 1, hanyuPinyinRecordFromChar.lastIndexOf(")")).split(",");
    }

    private boolean isValidRecord(String str) {
        return str != null && !str.equals("(none0)") && str.startsWith("(") && str.endsWith(")");
    }

    private String getHanyuPinyinRecordFromChar(char c) {
        String property = getUnicodeToHanyuPinyinTable().getProperty(Integer.toHexString(c).toUpperCase());
        if (isValidRecord(property)) {
            return property;
        }
        return null;
    }

    static ChineseToPinyinResource getInstance() {
        return ChineseToPinyinResourceHolder.theInstance;
    }

    private static class ChineseToPinyinResourceHolder {
        static final ChineseToPinyinResource theInstance = new ChineseToPinyinResource();

        private ChineseToPinyinResourceHolder() {
        }
    }

    class Field {
        static final String COMMA = ",";
        static final String LEFT_BRACKET = "(";
        static final String RIGHT_BRACKET = ")";

        Field() {
        }
    }
}
