package com.urise.webapp.util;

import com.urise.webapp.Config;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.storage.Storage;

import java.util.List;

public class SectionUtil {
    private Storage storage = Config.getINSTANCE().getStorage();

    public List<String> getList(AbstractSection section) {
        return ((ListSection) section).getTextList();
    }
}
