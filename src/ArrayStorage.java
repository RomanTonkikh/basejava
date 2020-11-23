/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    public static int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        if (size == 0) {
            storage[0] = resume;
            size++;
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(resume.uuid)) {
                    System.out.println("------Резюме с таким uuid уже есть в базе данных!-----");
                    break;
                } else if (i == size - 1) {
                    storage[size] = resume;
                    size++;
                    break;
                }
            }
        }
    }

    Resume get(String uuid) {
        Resume getResume = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                getResume = storage[i];
                break;
            }
        }
        return getResume;
    }

    void delete(String uuid) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                count = i;
                size--;

            }
        }
        for (int i = count; i < size - 1; i++) {
            storage[i] = storage[i + 1];
            storage[i + 1] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        System.arraycopy(storage, 0, allResume, 0, size);
        return allResume;
    }

    int size() {
        return size;
    }
}
