/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    public static int size = 0;

    void clear() {
        int count = 0;
        size = 0;
        for (Resume r : storage) {
            if (r != null) {
                storage[count] = null;
                count++;
            } else {
                break;
            }
        }
    }

    void save(Resume resume) {
        int count = 0;

        for (Resume r : storage) {
            if (r != null) {
                if (r.uuid.equals(resume.uuid)) {
                    System.out.println("------Резюме с таким uuid уже есть в базе данных!-----");
                    break;
                }
            }
            if (r == null) {
                storage[count] = resume;
                size++;
                break;
            }
            count++;
        }
    }

    Resume get(String uuid) {
        Resume getResume = null;
        for (Resume r : storage) {
            if (r == null) {
                break;
            } else if (r.uuid.equals(uuid)) {
                getResume = r;
                break;
            }
        }
        return getResume;
    }

    void delete(String uuid) {
        int count = 0;
        for (Resume r : storage) {
            if (r == null) {
                break;
            } else if (r.uuid.equals(uuid)) {
                storage[count] = null;
                size--;

            }
            count++;
        }

        for (int i = 0; i < count - 1; i++) {
            if (storage[i] == null) {
                storage[i] = storage[i + 1];
                storage[i + 1] = null;

            }
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int count = 0;

        for (Resume r : storage) {
            if (r != null) {
                count++;
            } else {
                break;
            }
        }
        Resume[] allResume = new Resume[count];
        System.arraycopy(storage, 0, allResume, 0, count);
        return allResume;
    }

    int size() {
        /*int count = 0;

        for (Resume r : storage) {
            if (r != null) {
                count++;
            } else {
                break;
            }
        }
        return count;*/
        return size;
    }
}
