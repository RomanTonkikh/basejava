package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void advancedSave(Resume r) {
        int index = getIndex(r.getUuid());
        System.arraycopy(storage, -index - 1, storage, -index, size);
        storage[-index - 1] = r;
    }

    protected void advancedDelete(String uuid) {
        int index = getIndex(uuid);
        System.arraycopy(storage, index + 1, storage, index, size);
        storage[size - 1] = null;
    }
}
