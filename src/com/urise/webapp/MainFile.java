package com.urise.webapp;

import java.io.*;
import java.util.Objects;

public class MainFile {

    public static void printFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = 0; i < Objects.requireNonNull(list).length; i++) {
                printFiles(list[i]);
            }
        } else {
            System.out.println(file.getCanonicalPath());
        }
    }

    public static void main(String[] args) throws Exception {

        File pathFile = new File("..\\basejava");
        printFiles(pathFile);

        /*String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File(".\\src\\com\\urise\\webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);

        }*/
    }
}
