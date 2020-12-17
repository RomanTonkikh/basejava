package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume resume) {
        int searchKey = checkException(resume.getUuid());
        advancedUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        if (searchKey >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        advancedSave(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        checkException(uuid);
        return advancedGet(uuid);
    }

    @Override
    public void delete(String uuid) {
        checkException(uuid);
        advancedDelete(uuid);
    }

    private int checkException(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract int getSearchKey(String uuid);

    protected abstract void advancedSave(int searchKey, Resume resume);

    protected abstract Resume advancedGet(String uuid);

    protected abstract void advancedDelete(String uuid);

    protected abstract void advancedUpdate(int searchKey, Resume resume);
}
