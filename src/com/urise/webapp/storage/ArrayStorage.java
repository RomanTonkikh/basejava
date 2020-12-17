package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveElement(int searchKey, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void fillElement(int searchKey) {
        storage[searchKey] = storage[size - 1];
    }
}

