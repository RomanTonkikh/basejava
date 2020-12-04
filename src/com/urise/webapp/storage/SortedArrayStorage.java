package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void advancedSave(int index, Resume resume) {
        int indexPosition = -index - 1;
        System.arraycopy(storage, indexPosition, storage, indexPosition + 1, size - indexPosition);
        storage[indexPosition] = resume;
    }

    protected void advancedDelete(int index) {
        int indexPosition = size - index - 1;
        if (indexPosition > 0) {
            System.arraycopy(storage, index + 1, storage, index, indexPosition);
        }
    }
}
