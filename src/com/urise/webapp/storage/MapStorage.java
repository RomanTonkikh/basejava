package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected int getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (uuid.equals(entry.getKey())) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    protected void advancedSave(int searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume advancedGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void advancedDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void advancedUpdate(int searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
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
