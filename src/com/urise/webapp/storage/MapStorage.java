package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    protected SortedMap<String, Resume> storage = new TreeMap<>();

    @Override
    protected int getIndex(String uuid) {
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            i++;
            if (uuid.equals(entry.getKey())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void advancedSave(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume advancedGet(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void advancedDelete(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void advancedUpdate(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void advancedClear() {
        storage.clear();
    }

    @Override
    public int advancedSize() {
        return storage.size();
    }

    @Override
    public Resume[] advancedGetAll() {
        Resume[] resume = new Resume[storage.size()];
        return storage.values().toArray(resume);
    }
}
