package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;

import java.io.*;

public interface Serializer {

    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume resume, OutputStream os) throws IOException;
}
