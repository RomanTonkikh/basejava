package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (findUuidAndNull(resume.getUuid())) {
            return;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                storage[i].setUuid(resume.getUuid());
                System.out.println("\nРезюме обновлено.");
                break;
            }
        }
    }

    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            return;
        }
        if (size >= storage.length) {
            System.out.println("\nНевозможно добавить резюме " + resume.getUuid() + ", так как база данных переполнена");
            return;
        }
        if (size == 0) {
            storage[0] = resume;
            size++;
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    System.out.println("\nРезюме с именем " + "\"" + resume.getUuid() + "\"" + " уже есть в базе данных! Для обновления резюме используйте команду update.");
                    break;
                } else if (i == size - 1) {
                    storage[size] = resume;
                    size++;
                    break;
                }
            }
        }
    }

    public Resume get(String uuid) {

        if (findUuidAndNull(uuid)) {
            return null;
        }

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return storage[i];
            }
        }
        return null;
    }


    public void delete(String uuid) {
        if (findUuidAndNull(uuid)) {
            return;
        }
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResume;
        allResume = Arrays.copyOf(storage, size);
        return allResume;
    }

    public int size() {
        return size;
    }

    public boolean findUuidAndNull(String uuid) {
        if (uuid == null) {
            System.out.println("\nОшибка ввода! uuid не должен быть пустым");
            return true;
        }
        if (size == 0) {
            System.out.println("\nБаза данных резюме не содержит записей.");
            return true;
        }
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (!uuid.equals(storage[i].getUuid())) {
                count++;
            }
            if (size == count) {
                System.out.println("\nРезюме с именем " + "\"" + uuid + "\"" + " отсутствует в базе данных.");
                return true;
            }
        }
        return false;
    }
}
