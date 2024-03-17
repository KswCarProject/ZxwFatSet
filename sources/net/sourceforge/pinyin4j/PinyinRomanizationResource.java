package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.ParseException;
import com.hp.hpl.sparta.Parser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

class PinyinRomanizationResource {
    private Document pinyinMappingDoc;

    private void setPinyinMappingDoc(Document document) {
        this.pinyinMappingDoc = document;
    }

    /* access modifiers changed from: package-private */
    public Document getPinyinMappingDoc() {
        return this.pinyinMappingDoc;
    }

    private PinyinRomanizationResource() {
        initializeResource();
    }

    private void initializeResource() {
        try {
            setPinyinMappingDoc(Parser.parse("", (InputStream) ResourceHelper.getResourceInputStream("/pinyindb/pinyin_mapping.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ParseException e3) {
            e3.printStackTrace();
        }
    }

    static PinyinRomanizationResource getInstance() {
        return PinyinRomanizationSystemResourceHolder.theInstance;
    }

    private static class PinyinRomanizationSystemResourceHolder {
        static final PinyinRomanizationResource theInstance = new PinyinRomanizationResource();

        private PinyinRomanizationSystemResourceHolder() {
        }
    }
}
