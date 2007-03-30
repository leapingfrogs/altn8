package com.leapingfrogs.idea.plugins.alternate.action.support;

import java.util.*;

public class JavaAlternateFinder implements AlternateFinder {
    private Map mappings;

    public JavaAlternateFinder() {
        mappings = new HashMap();
        mappings.put("^test_(.*)\\.rb$", new String[]{"$1.sp"});
        mappings.put("^test_(.*)\\.xml$", new String[]{"$1.sp"});
        mappings.put("^(.*)\\.sp$", new String[]{"test_$1.xml", "test_$1.rb"});
    }

    public String[] getAlternateNames(String currentFileName) {
        List names = new ArrayList();

        for (Iterator iterator = mappings.keySet().iterator(); iterator.hasNext();) {
            String matchPattern = (String) iterator.next();
            String[] replacePatterns = (String[]) mappings.get(matchPattern);

            if (currentFileName.matches(matchPattern)) {
                for (int i = 0; i < replacePatterns.length; i++) {
                    String newName = currentFileName.replaceFirst(matchPattern, replacePatterns[i]);
                    names.add(newName);
                }
            }
        }
        return (String[]) names.toArray(new String[0]);
    }
}
