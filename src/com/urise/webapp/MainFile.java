package com.urise.webapp;

import java.io.*;

public class MainFile {

    public static void printFiles(File dir) throws IOException {
        File[] files = dir.listFiles();

        if (files != null) {

            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("\n" + file.getAbsolutePath() + "\\" + "\n" );
                    printFiles(file);
                } else if (file.isFile()) {
                    System.out.println("\t" + file.getName());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        File pathFile = new File(".\\src\\com\\urise\\webapp");
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
