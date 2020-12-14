package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void advancedSave(int index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        int indexPosition = -index - 1;
        System.arraycopy(storage, indexPosition, storage, indexPosition + 1, size - indexPosition);
        storage[indexPosition] = resume;
        size++;
    }

    @Override
    public Resume advancedGet(int index) {
        return storage[index];
    }

    @Override
    protected void advancedDelete(int index) {
        int indexPosition = size - index - 1;
        if (indexPosition > 0) {
            System.arraycopy(storage, index + 1, storage, index, indexPosition);
        }
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void advancedUpdate(int index, Resume resume) {
        storage[index] = resume;
    }
}
