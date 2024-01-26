package com.admin.tool;

import java.util.List;
import java.util.Map;

public class Common {

    public static String getType(Object obj) {
        if (obj instanceof String) {
            return "String";
        } else if (obj instanceof Integer) {
            return "Integer";
        } else if (obj instanceof Double) {
            return "Double";
        } else if (obj instanceof Boolean) {
            return "Boolean";
        } else if (obj instanceof List) {
            return "List";
        } else if (obj instanceof Map) {
            return "Map";
        } else {
            return "Unknown";
        }
    }


}
