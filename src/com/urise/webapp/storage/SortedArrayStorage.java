package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveElement(int searchKey, Resume resume) {
        int indexPosition = -searchKey - 1;
        System.arraycopy(storage, indexPosition, storage, indexPosition + 1, size - indexPosition);
        storage[indexPosition] = resume;
    }

    @Override
    protected void fillElement(int searchKey) {
        int indexPosition = size - searchKey - 1;
        if (indexPosition > 0) {
            System.arraycopy(storage, searchKey + 1, storage, searchKey, indexPosition);
        }
    }
}
