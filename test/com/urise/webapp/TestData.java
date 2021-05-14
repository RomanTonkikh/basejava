package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume RESUME_1 = fillResumeForElon(UUID_1, "Elon Musk");
    public static final Resume RESUME_2 = fillResume(UUID_2, "Mark Zuckerberg");
    public static final Resume RESUME_3 = fillResume(UUID_3, "Bill Gates");
    public static final Resume RESUME_4 = fillResume(UUID_4, "Steve Jobs");

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(ContactType.PHONE, "999-8888-9999");
        resume.setContact(ContactType.SKYPE, "romanSkype");
        resume.setContact(ContactType.EMAIL, "roman@Email");
        resume.setContact(ContactType.LINKEDIN, "romanLinkedin");
        resume.setContact(ContactType.GITHUB, "romanGithub");
        resume.setContact(ContactType.STACKOVERFLOW, "romanStackoverflow");
        resume.setContact(ContactType.HOMEPAGE, "romanHomepage");

        resume.setSection(SectionType.OBJECTIVE, new TextSection("Ученик"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Трудолюбие, целеустремленность, активность, коммуникабельность"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection("Учусь", "Учусь", "Учусь"));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection("Java syntax", "Java core"));
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization("JavaRush", null,
                new Organization.Position(2020, Month.AUGUST, 2020, Month.SEPTEMBER, "Ученик", "Изучал Java syntax"),
                new Organization.Position(2020, Month.SEPTEMBER, 2020, Month.OCTOBER, "Ученик", null))));
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(new Organization("Java Online Projects", "https://javaops.ru/",
                new Organization.Position(2020, Month.NOVEMBER, "Ученик", "Изучаю Java на примере создания web приложения"))));
        return resume;
    }

    public static Resume fillResumeForElon(String uuid, String fullName) {
              return new Resume(uuid, fullName);
    }
}

