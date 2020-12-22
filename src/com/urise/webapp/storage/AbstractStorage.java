package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume resume) {
        Object searchKey = getKeyIfResumeExist(resume.getUuid());
        advancedUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getKeyIfResumeNotExist(resume.getUuid());
        advancedSave(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getKeyIfResumeExist(uuid);
        advancedDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getKeyIfResumeExist(uuid);
        return advancedGet(searchKey);
    }

    private Object getKeyIfResumeExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getKeyIfResumeNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void advancedSave(Object object, Resume resume);

    protected abstract Resume advancedGet(Object object);

    protected abstract void advancedDelete(Object object);

    protected abstract void advancedUpdate(Object object, Resume resume);
}
