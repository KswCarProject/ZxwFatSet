package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Element;
import com.hp.hpl.sparta.ParseException;

class GwoyeuRomatzyhTranslator {
    private static String[] tones = {"_I", "_II", "_III", "_IV", "_V"};

    GwoyeuRomatzyhTranslator() {
    }

    static String convertHanyuPinyinToGwoyeuRomatzyh(String str) {
        String extractPinyinString = TextHelper.extractPinyinString(str);
        String extractToneNumber = TextHelper.extractToneNumber(str);
        try {
            Element xpathSelectElement = GwoyeuRomatzyhResource.getInstance().getPinyinToGwoyeuMappingDoc().xpathSelectElement("//" + PinyinRomanizationType.HANYU_PINYIN.getTagName() + "[text()='" + extractPinyinString + "']");
            if (xpathSelectElement != null) {
                return xpathSelectElement.xpathSelectString("../" + PinyinRomanizationType.GWOYEU_ROMATZYH.getTagName() + tones[Integer.parseInt(extractToneNumber) - 1] + "/text()");
            }
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
