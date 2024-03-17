package com.szchoiceway.fatset.util;

import java.io.InputStream;
import java.util.List;

public interface KSW_CarTypeList_Parser {
    List<KSW_CarTypeList_Info> parse(InputStream inputStream) throws Exception;

    String serialize(List<KSW_CarTypeList_Info> list) throws Exception;
}
