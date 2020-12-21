package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object getSearchKey(String uuid) {
        Resume index = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, index);
    }

    @Override
    protected void saveElement(int index, Resume resume) {
        int indexPosition = -index - 1;
        System.arraycopy(storage, indexPosition, storage, indexPosition + 1, size - indexPosition);
        storage[indexPosition] = resume;
    }

    @Override
    protected void fillElement(int index) {
        int indexPosition = size - index - 1;
        if (indexPosition > 0) {
            System.arraycopy(storage, index + 1, storage, index, indexPosition);
        }
    }
}
