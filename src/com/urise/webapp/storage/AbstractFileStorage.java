package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readeble/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getListResume() {
        File[] fileList = directory.listFiles();
        List<Resume> resumeList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(fileList).length; i++) {
            try {
                resumeList.add(doRead(fileList[i]));
            } catch (IOException e) {
                throw new StorageException("file read error", fileList[i].getName(), e);
            }
        }
        return resumeList;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void advancedSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("file save error", file.getName(), e);
        }
    }

    @Override
    protected Resume advancedGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("file read error", file.getName(), e);
        }
    }

    @Override
    protected void advancedDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("file deletion error", file.getName());
        }
    }

    @Override
    protected void advancedUpdate(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("file update error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        File[] fileList = directory.listFiles();
        for (int i = 0; i < Objects.requireNonNull(fileList).length; i++) {
            advancedDelete(fileList[i]);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    protected abstract Resume doRead(File file) throws IOException;

    protected abstract void doWrite(Resume resume, File file) throws IOException;
}
