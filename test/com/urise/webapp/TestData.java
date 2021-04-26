package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume RESUME_1 = fillResume(UUID_1, "Elon Musk");
    public static final Resume RESUME_2 = fillResume(UUID_2, "Mark Zuckerberg");
    public static final Resume RESUME_3 = fillResume(UUID_3, "Bill Gates");
    public static final Resume RESUME_4 = fillResume(UUID_4, "Steve Jobs");

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "999-8888-9999");
        resume.addContact(ContactType.SKYPE, "romanSkype");
        resume.addContact(ContactType.EMAIL, "roman@Email");
        resume.addContact(ContactType.LINKEDIN, "romanLinkedin");
        resume.addContact(ContactType.GITHUB, "romanGithub");
        resume.addContact(ContactType.STACKOVERFLOW, "romanStackoverflow");
        resume.addContact(ContactType.HOMEPAGE, "romanHomepage");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ученик"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Трудолюбие, целеустремленность, активность, коммуникабельность"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Учусь", "Учусь", "Учусь"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("Java syntax", "Java core"));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization("JavaRush", null,
                new Organization.Position(2020, Month.AUGUST, 2020, Month.SEPTEMBER, "Ученик", "Изучал Java syntax"),
                new Organization.Position(2020, Month.SEPTEMBER, 2020, Month.OCTOBER, "Ученик", null))));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(new Organization("Java Online Projects", "https://javaops.ru/",
                new Organization.Position(2020, Month.NOVEMBER, "Ученик", "Изучаю Java на примере создания web приложения"))));
        return resume;
    }
}

