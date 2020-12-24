package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    public final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

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
    public List<Resume> getAllSorted() {
        List<Resume> list = getListResume();
        list.sort(RESUME_COMPARATOR);
        return list;
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
    protected abstract List<Resume> getListResume();

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void advancedSave(Object searchKey, Resume resume);

    protected abstract Resume advancedGet(Object searchKey);

    protected abstract void advancedDelete(Object searchKey);

    protected abstract void advancedUpdate(Object searchKey, Resume resume);
}

