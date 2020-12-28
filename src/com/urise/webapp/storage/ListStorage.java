package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    protected void advancedSave(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume advancedGet(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void advancedDelete(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected void advancedUpdate(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public List<Resume> getListResume() {
        return storage;
    }
}
