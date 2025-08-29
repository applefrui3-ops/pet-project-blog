package com.example.personal_blog.util;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BreadcrumbUtil {

    private BreadcrumbUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Map<String, String> createBreadcrumbs(String... items) {
        Map<String, String> breadcrumbs = new LinkedHashMap<>();

        for (int i = 0; i < items.length; i += 2) {
            String key = items[i];
            String value = (i + 1 < items.length) ? items[i + 1] : "#";
            breadcrumbs.put(key, value);
        }

        return breadcrumbs;
    }
}
