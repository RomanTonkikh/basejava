package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume resume;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid fullName | delete uuid | get uuid | update uuid |clear | exit): ");
            String[] params = reader.readLine().trim().split(" ");
            if (params.length < 1 || params.length > 4) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = "MisterX";
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            if (params.length == 3) {
                uuid = params[1].intern();
                fullName = params[2];
            }
            if (params.length == 4) {
                uuid = params[1].intern();
                fullName = params[2] + " " + params[3];
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    resume = new Resume(uuid, fullName);
                    ARRAY_STORAGE.save(resume);
                    printAll();
                    break;
                case "update":
                    resume = new Resume(uuid, fullName);
                    ARRAY_STORAGE.update(resume);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> list = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (list.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : list) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}