package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;


public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void advancedSave(int index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        storage[size] = resume;
        size++;
    }

    @Override
    public Resume advancedGet(int index) {
        return storage[index];
    }

    @Override
    protected void advancedDelete(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void advancedUpdate(int index, Resume resume) {
        storage[index] = resume;
    }
}
