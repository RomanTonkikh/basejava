package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int searchKey = getIndex(resume.getUuid());
        if (searchKey < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        advancedUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        int searchKey = getIndex(resume.getUuid());
        if (searchKey >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        advancedSave(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return advancedGet(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        advancedDelete(uuid);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void advancedSave(int searchKey, Resume resume);

    protected abstract Resume advancedGet(String uuid);

    protected abstract void advancedDelete(String uuid);

    protected abstract void advancedUpdate(int searchKey, Resume resume);

}
