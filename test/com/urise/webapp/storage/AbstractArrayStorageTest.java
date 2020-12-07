package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_4 = "uuid_4";
    private static final String UUID_5 = "uuid_5";
    private static final String UUID_6 = "uuid_6";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_3));
        Assert.assertEquals(new Resume(UUID_3), storage.get("uuid_3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_4));
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(new Resume(UUID_4), storage.get("uuid_4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void saveOverflow() {
        try {
            storage.save(new Resume(UUID_4));
            storage.save(new Resume(UUID_5));
            storage.save(new Resume(UUID_6));
        } catch (StorageException e) {
            if (storage.size() < STORAGE_LIMIT) {
                Assert.fail("Overflowing happened ahead of time");
            }
        }
    }

    @Test
    public void delete() {
        storage.delete("uuid_3");
        try {
            storage.get("uuid_3");
            Assert.fail("Resume not deleted");
        } catch (NotExistStorageException e) {
            System.out.println("Resume deleted");
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("uuid_6");
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] result = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(result, storage.getAll());
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_3), this.storage.get("uuid_3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("Dummy");
    }
}