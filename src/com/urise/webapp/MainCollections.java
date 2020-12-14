package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;
import com.urise.webapp.storage.Storage;

import java.util.*;

public class MainCollections {

    private final static Storage LIST_STORAGE = new ListStorage();

    private static final String UUID_1 = "uuid_1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid_2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid_3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid_4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    static void printAll() {
        for (Resume resume : LIST_STORAGE.getAll()) {
            System.out.println(resume);
        }
    }

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);
        for (Resume r : collection) {
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID_1)) {
                //collection.remove(r);
                System.out.println(r.getUuid());
            }
        }
        collection.removeIf(r -> Objects.equals(r.getUuid(), UUID_1));
        System.out.println(collection.toString());

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);

        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
