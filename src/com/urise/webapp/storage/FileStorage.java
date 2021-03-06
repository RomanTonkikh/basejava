package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Serializer serializer;

    protected FileStorage(File directory, Serializer serializer) {

        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readeble/writable");
        }
        this.directory = directory;
        this.serializer = serializer;
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] fileList = getFileList();
        List<Resume> resumeList = new ArrayList<>(fileList.length);
        for (File file : fileList) {
            resumeList.add(doGet(file));
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
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file" + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("file read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("file deletion error", file.getName());
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("file write  error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        File[] fileList = getFileList();
        for (File file : fileList) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] fileList = getFileList();
        return fileList.length;
    }

    private File[] getFileList() {
        File[] fileList = directory.listFiles();
        if (fileList == null) {
            throw new StorageException("Directory read error", null, null);
        }
        return fileList;
    }
}
