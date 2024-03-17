package net.sourceforge.pinyin4j.format;

public final class HanyuPinyinOutputFormat {
    private HanyuPinyinCaseType caseType;
    private HanyuPinyinToneType toneType;
    private HanyuPinyinVCharType vCharType;

    public HanyuPinyinOutputFormat() {
        restoreDefault();
    }

    public void restoreDefault() {
        this.vCharType = HanyuPinyinVCharType.WITH_U_AND_COLON;
        this.caseType = HanyuPinyinCaseType.LOWERCASE;
        this.toneType = HanyuPinyinToneType.WITH_TONE_NUMBER;
    }

    public HanyuPinyinCaseType getCaseType() {
        return this.caseType;
    }

    public void setCaseType(HanyuPinyinCaseType hanyuPinyinCaseType) {
        this.caseType = hanyuPinyinCaseType;
    }

    public HanyuPinyinToneType getToneType() {
        return this.toneType;
    }

    public void setToneType(HanyuPinyinToneType hanyuPinyinToneType) {
        this.toneType = hanyuPinyinToneType;
    }

    public HanyuPinyinVCharType getVCharType() {
        return this.vCharType;
    }

    public void setVCharType(HanyuPinyinVCharType hanyuPinyinVCharType) {
        this.vCharType = hanyuPinyinVCharType;
    }
}
