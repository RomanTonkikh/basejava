package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    public final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void update(Resume resume) {
        SK searchKey = getKeyIfResumeExist(resume.getUuid());
        advancedUpdate(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        SK searchKey = getKeyIfResumeNotExist(resume.getUuid());
        advancedSave(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getKeyIfResumeExist(uuid);
        advancedDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getKeyIfResumeExist(uuid);
        return advancedGet(searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = getListResume();
        list.sort(RESUME_COMPARATOR);
        return list;
    }


    private SK getKeyIfResumeExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getKeyIfResumeNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> getListResume();

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void advancedSave(SK searchKey, Resume resume);

    protected abstract Resume advancedGet(SK searchKey);

    protected abstract void advancedDelete(SK searchKey);

    protected abstract void advancedUpdate(SK searchKey, Resume resume);
}

