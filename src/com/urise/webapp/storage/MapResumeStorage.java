package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void advancedSave(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume advancedGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void advancedDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected void advancedUpdate(Object searchKey, Resume resume) {
        storage.put(((Resume) searchKey).getUuid(), resume);
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
