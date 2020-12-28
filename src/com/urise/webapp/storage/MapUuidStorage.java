package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return searchKey != null;
    }

    @Override
    protected void advancedSave(String searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume advancedGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void advancedDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void advancedUpdate(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public List<Resume> getListResume() {
       return new ArrayList<>(storage.values());
    }
}
