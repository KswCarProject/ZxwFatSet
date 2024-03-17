package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.ParseException;
import com.hp.hpl.sparta.Parser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

class GwoyeuRomatzyhResource {
    private Document pinyinToGwoyeuMappingDoc;

    private void setPinyinToGwoyeuMappingDoc(Document document) {
        this.pinyinToGwoyeuMappingDoc = document;
    }

    /* access modifiers changed from: package-private */
    public Document getPinyinToGwoyeuMappingDoc() {
        return this.pinyinToGwoyeuMappingDoc;
    }

    private GwoyeuRomatzyhResource() {
        initializeResource();
    }

    private void initializeResource() {
        try {
            setPinyinToGwoyeuMappingDoc(Parser.parse("", (InputStream) ResourceHelper.getResourceInputStream("/pinyindb/pinyin_gwoyeu_mapping.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ParseException e3) {
            e3.printStackTrace();
        }
    }

    static GwoyeuRomatzyhResource getInstance() {
        return GwoyeuRomatzyhSystemResourceHolder.theInstance;
    }

    private static class GwoyeuRomatzyhSystemResourceHolder {
        static final GwoyeuRomatzyhResource theInstance = new GwoyeuRomatzyhResource();

        private GwoyeuRomatzyhSystemResourceHolder() {
        }
    }
}
