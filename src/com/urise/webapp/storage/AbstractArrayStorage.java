package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void advancedClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] advancedGetAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public Resume advancedGet(int index, String uuid) {
        return storage[index];
    }

    @Override
    public int advancedSize() {
        return size;
    }
}

