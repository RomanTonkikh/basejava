package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_4 = "uuid_4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

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
        Resume result = new Resume(UUID_3);
        storage.update(result);
        Assert.assertEquals(result, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_4));
    }

    @Test
    public void save() {
        Resume result = new Resume(UUID_4);
        storage.save(result);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(result, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(storage.get(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume("uuid_" + i + 1));
            } catch (StorageException e) {
                Assert.fail("Overflowing happened ahead of time");
            }
        }
        storage.save(new Resume(UUID_4));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Resume resume1 = new Resume(UUID_1);
        Resume resume2 = new Resume(UUID_2);
        Resume resume3 = new Resume(UUID_3);
        Resume[] result = {resume1, resume2, resume3};
        Assert.assertArrayEquals(result, storage.getAll());
    }

    @Test
    public void get() {
        Resume result = new Resume(UUID_3);
        Assert.assertEquals(result, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("Dummy");
    }
}