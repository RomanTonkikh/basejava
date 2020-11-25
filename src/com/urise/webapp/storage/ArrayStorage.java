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
        if (size > 0) {
            Arrays.fill(storage, 0, size - 1, null);
            size = 0;
        }
    }

    public void update(Resume resume) {
        if (getUuidNumber(resume.getUuid()) < 0) {
            System.out.println("\nРезюме с именем " + "\"" + resume.getUuid() + "\"" + " отсутствует в базе данных.");
            return;
        }
        storage[getUuidNumber(resume.getUuid())].setUuid(resume.getUuid());
        System.out.println("\nРезюме обновлено.");
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("\nНевозможно добавить резюме " + resume.getUuid() + ", так как база данных переполнена");
            return;
        }
        if (size == 0) {
            storage[0] = resume;
            size++;
        } else {
            if (getUuidNumber(resume.getUuid()) >= 0) {
                System.out.println("\nРезюме с именем " + "\"" + resume.getUuid() + "\"" + " уже есть в базе данных! Для обновления резюме используйте команду update.");
            } else {
                storage[size] = resume;
                size++;
            }
        }
    }

    public Resume get(String uuid) {
        if (getUuidNumber(uuid) < 0) {
            System.out.println("\nРезюме с именем " + "\"" + uuid + "\"" + " отсутствует в базе данных.");
            return null;
        }
        return storage[getUuidNumber(uuid)];
    }

    public void delete(String uuid) {
        if (getUuidNumber(uuid) < 0) {
            System.out.println("\nРезюме с именем " + "\"" + uuid + "\"" + " отсутствует в базе данных.");
            return;
        }
        storage[getUuidNumber(uuid)] = storage[size - 1];
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

    public int getUuidNumber(String uuid) { // метод осуществляет перебор i < size и возвращает номер совпавшего резюме для последующих операций с ним, если возвращает -1, совпадений не найдено.
        int number = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                number = i;
                break;
            }
        }
        return number;
    }
}