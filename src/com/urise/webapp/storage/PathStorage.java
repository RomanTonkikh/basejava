package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Serializer serializer;

    protected PathStorage(String dir, Serializer serializer) {
        this.directory = Paths.get(dir);
        this.serializer = serializer;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "Is not directory or is not writable");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getPathList().stream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file" + path.getParent(), path.getFileName().toString(), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Path deletion error", path.getFileName().toString());
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", path.getFileName().toString(), e);
        }
    }

    @Override
    public void clear() {
        List<Path> pathList = getPathList();
        for (Path path : pathList) {
            doDelete(path);
        }
    }

    @Override
    public int size() {
        List<Path> pathList = getPathList();
        return pathList.size();
    }

    private List<Path> getPathList() {
        List<Path> pathList;
        try {
            pathList = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return pathList;
    }
}