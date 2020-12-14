package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;


public abstract class AbstractStorage implements Storage {
    public void clear() {
        advancedClear();
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        advancedUpdate(index, resume);
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        advancedSave(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return advancedGet(index, uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        advancedDelete(index, uuid);
    }

    @Override
    public Resume[] getAll() {
        return advancedGetAll();
    }

    @Override
    public int size() {
        return advancedSize();
    }

    protected abstract int getIndex(String uuid);

    protected abstract void advancedSave(int index, Resume resume);

    protected abstract Resume advancedGet(int index, String uuid);

    protected abstract void advancedDelete(int index, String uuid);

    protected abstract void advancedUpdate(int index, Resume resume);

    protected abstract void advancedClear();

    protected abstract int advancedSize();

    protected abstract Resume[] advancedGetAll();


}
