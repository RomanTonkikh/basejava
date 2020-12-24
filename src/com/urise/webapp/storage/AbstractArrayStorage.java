package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected void advancedUpdate(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void advancedSave(Object index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement((int) index, resume);
        size++;
    }

    @Override
    protected void advancedDelete(Object index) {
        fillElement((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume advancedGet(Object index) {
        return storage[(int) index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public List<Resume> getListResume() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    protected abstract void saveElement(int index, Resume resume);

    protected abstract void fillElement(int index);

}