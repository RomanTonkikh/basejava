package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void advancedSave(int index, Resume resume) {
        storage.add(resume);
        Collections.sort(storage);
    }
    @Override
    public Resume advancedGet(int index) {
        return storage.get(index);
    }

    @Override
    protected void advancedDelete(int index) {
        storage.remove(index);
    }

    @Override
    protected void advancedUpdate(int index, Resume resume) {
        storage.set(index, resume);
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
        return storage.toArray(resume);
    }
}
