package com.szchoiceway.fatset.util;

import android.util.Xml;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

public class KSW_CarTypeList_Pull_Parser implements KSW_CarTypeList_Parser {
    public List<KSW_CarTypeList_Info> parse(InputStream inputStream) throws Exception {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        ArrayList arrayList = null;
        KSW_CarTypeList_Info kSW_CarTypeList_Info = null;
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 0) {
                arrayList = new ArrayList();
            } else if (eventType != 2) {
                if (eventType == 3 && newPullParser.getName().equals("model")) {
                    arrayList.add(kSW_CarTypeList_Info);
                    kSW_CarTypeList_Info = null;
                }
            } else if (newPullParser.getName().equals("model")) {
                kSW_CarTypeList_Info = new KSW_CarTypeList_Info();
            } else if (newPullParser.getName().equals("id")) {
                newPullParser.next();
                kSW_CarTypeList_Info.setCarId(Integer.parseInt(newPullParser.getText()));
            } else if (newPullParser.getName().equals("name")) {
                newPullParser.next();
                kSW_CarTypeList_Info.setCarName(newPullParser.getText());
            }
        }
        return arrayList;
    }

    public String serialize(List<KSW_CarTypeList_Info> list) throws Exception {
        XmlSerializer newSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        newSerializer.setOutput(stringWriter);
        newSerializer.startDocument("UTF-8", true);
        newSerializer.startTag("", "cartype");
        for (KSW_CarTypeList_Info next : list) {
            newSerializer.startTag("", "model");
            newSerializer.startTag("", "id");
            newSerializer.text(next.getCarName());
            newSerializer.endTag("", "id");
            newSerializer.startTag("", "name");
            newSerializer.text(next.getCarName());
            newSerializer.endTag("", "name");
            newSerializer.endTag("", "model");
        }
        newSerializer.endTag("", "cartype");
        newSerializer.endDocument();
        return stringWriter.toString();
    }
}
