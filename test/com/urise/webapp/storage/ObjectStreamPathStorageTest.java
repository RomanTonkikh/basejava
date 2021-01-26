package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectSerializer()));
    }
}
