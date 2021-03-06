package com.urise.webapp;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPS = "/resumes.properties";
//    private static final File PROPS;

    //    static {
//        final String rootDir = System.getProperty("root");
//        PROPS = new File(rootDir == null ? "." : rootDir,
//                "config\\resumes.properties");
//    }
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;

    public static Config getINSTANCE() {
        return INSTANCE;
    }

//    private Config() {
//        try (InputStream is = new FileInputStream(PROPS)) {
//            Properties props = new Properties();
//            props.load(is);
//            storageDir = new File(props.getProperty("storage.dir"));
//            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"),
//                    props.getProperty("db.password"));
//        } catch (IOException e) {
//            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
//        }
//    }
    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"),
                    props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
