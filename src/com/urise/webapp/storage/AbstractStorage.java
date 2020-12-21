package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedObjectKey(resume.getUuid());
        advancedUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistObjectKey(resume.getUuid());
        advancedSave(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedObjectKey(uuid);
        advancedDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedObjectKey(uuid);
        return advancedGet(searchKey);
    }

    private Object getExistedObjectKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!checkExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistObjectKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (checkExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean checkExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void advancedSave(Object object, Resume resume);

    protected abstract Resume advancedGet(Object object);

    protected abstract void advancedDelete(Object object);

    protected abstract void advancedUpdate(Object object, Resume resume);
}
