package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("\nРезюме с именем " + "\"" + resume.getUuid() + "\"" + " отсутствует в базе данных.");
            return;
        }
        storage[index] = resume;
        System.out.println("\nРезюме обновлено.");
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("\nНевозможно добавить резюме " + resume.getUuid() + ", так как база данных переполнена");
            return;
        }
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("\nРезюме с именем " + "\"" + resume.getUuid() + "\"" + " уже есть в базе данных! Для обновления резюме используйте команду update.");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("\nРезюме с именем " + "\"" + uuid + "\"" + " отсутствует в базе данных.");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("\nРезюме с именем " + "\"" + uuid + "\"" + " отсутствует в базе данных.");
            return;
        }
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    /**
     * метод осуществляет перебор i < size и возвращает индекс
     * для последующих операций с ним, если возвращает -1, совпадений не найдено.
     */

    private int getIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
                break;
            }
        }
        return index;
    }
}