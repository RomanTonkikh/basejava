package com.urise.webapp;

import java.io.*;
import java.util.*;

public class MainFile {
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        File dir = new File("./src");
        printAllFiles(dir);
    }

    static void printAllFiles(File dir) {

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            System.out.println(sb.toString() + file.getName());
            if (file.isDirectory()) {
                sb.append("   ");
                printAllFiles(file);
            }
        }
        sb.delete(0, 3);
    }
}


