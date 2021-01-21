package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.*;

public interface IOStorage {

    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume resume, OutputStream os) throws IOException;
}
