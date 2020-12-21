package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (uuid.equals(entry.getKey())) {
                return uuid;
            }
        }
        return null;
    }

    @Override
    protected boolean checkExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void advancedSave(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume advancedGet(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    protected void advancedDelete(Object searchKey) {
        storage.remove(searchKey.toString());
    }

    @Override
    protected void advancedUpdate(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public Resume[] getAll() {
        Resume[] resume = new Resume[storage.size()];
        return storage.values().toArray(resume);
    }
}
