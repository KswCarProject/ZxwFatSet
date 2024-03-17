package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Element;
import com.hp.hpl.sparta.ParseException;

class PinyinRomanizationTranslator {
    PinyinRomanizationTranslator() {
    }

    static String convertRomanizationSystem(String str, PinyinRomanizationType pinyinRomanizationType, PinyinRomanizationType pinyinRomanizationType2) {
        String extractPinyinString = TextHelper.extractPinyinString(str);
        String extractToneNumber = TextHelper.extractToneNumber(str);
        try {
            Element xpathSelectElement = PinyinRomanizationResource.getInstance().getPinyinMappingDoc().xpathSelectElement("//" + pinyinRomanizationType.getTagName() + "[text()='" + extractPinyinString + "']");
            if (xpathSelectElement == null) {
                return null;
            }
            return xpathSelectElement.xpathSelectString("../" + pinyinRomanizationType2.getTagName() + "/text()") + extractToneNumber;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
