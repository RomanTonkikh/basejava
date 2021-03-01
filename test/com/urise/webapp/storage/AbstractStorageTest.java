package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.ResumeTestData.fillResume;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getINSTANCE().getStorageDir();
    protected final Storage storage;

    protected static final String UUID_1 = "uuid_1";
    protected static final String UUID_2 = "uuid_2";
    protected static final String UUID_3 = "uuid_3";
    protected static final String UUID_4 = "uuid_4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Elon Musk");
        RESUME_2 = new Resume(UUID_2, "Mark Zuckerberg");
        RESUME_3 = new Resume(UUID_3, "Bill Gates");
        RESUME_4 = new Resume(UUID_4, "Steve Jobs");
    }
/*
    protected static final Resume RESUME_1 = fillResume(UUID_1, "Elon Musk");
    protected static final Resume RESUME_2 = fillResume(UUID_2, "Mark Zuckerberg");
    protected static final Resume RESUME_3 = fillResume(UUID_3, "Bill Gates");
    protected static final Resume RESUME_4 = fillResume(UUID_4, "Steve Jobs");
*/

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume result = new Resume(UUID_3, "Bill Gates");
        storage.update(result);
        assertEquals(result, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = new ArrayList<>(storage.getAllSorted());
        List<Resume> resultList = new ArrayList<>(Arrays.asList(RESUME_3, RESUME_1, RESUME_2));
        assertEquals(resultList, list);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("Dummy");
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}