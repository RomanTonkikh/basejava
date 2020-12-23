package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected void advancedSave(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume advancedGet(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void advancedDelete(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected void advancedUpdate(Object index, Resume resume) {
        storage.set((int) index, resume);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public List<Resume> getAllSorted() {
        storage.sort(RESUME_COMPARATOR);
        return storage;
    }
}
