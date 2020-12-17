package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public Resume advancedGet(String uuid) {
        return storage[getSearchKey(uuid)];
    }

    public int size() {
        return size;
    }

    @Override
    protected void advancedSave(int searchKey, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement(searchKey, resume);
        size++;
    }

    @Override
    protected void advancedDelete(String uuid) {
        fillElement(getSearchKey(uuid));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void advancedUpdate(int searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    protected abstract void saveElement(int searchKey, Resume resume);

    protected abstract void fillElement(int searchKey);

}