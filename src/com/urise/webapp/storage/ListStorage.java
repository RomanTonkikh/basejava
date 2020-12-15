package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

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
    protected void advancedSave(int searchKey, Resume resume) {
        storage.add(resume);
        Collections.sort(storage);
    }

    @Override
    public Resume advancedGet(String uuid) {
        return storage.get(getIndex(uuid));
    }

    @Override
    protected void advancedDelete(String uuid) {
        storage.remove(getIndex(uuid));
    }

    @Override
    protected void advancedUpdate(int searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public Resume[] getAll() {
        Resume[] resume = new Resume[storage.size()];
        return storage.toArray(resume);
    }
}
