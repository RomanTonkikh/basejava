package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    /* private static class ResumeComparator implements Comparator<Resume> {
         @Override
         public int compare(Resume o1, Resume o2) {
             return o1.getUuid().compareTo(o2.getUuid());
         }
     }*/
    //private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Object getSearchKey(String uuid) {
        Resume index = new Resume(uuid, "MisterX");

        return Arrays.binarySearch(storage, 0, size, index, RESUME_COMPARATOR);
    }

    @Override
    protected void saveElement(int index, Resume resume) {
        int indexPosition = -index - 1;
        System.arraycopy(storage, indexPosition, storage, indexPosition + 1, size - indexPosition);
        storage[indexPosition] = resume;
    }

    @Override
    protected void fillElement(int index) {
        int indexPosition = size - index - 1;
        if (indexPosition > 0) {
            System.arraycopy(storage, index + 1, storage, index, indexPosition);
        }
    }
}

