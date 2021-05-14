package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume1 = fillResume("uuid_1", "Roman Tonkikh");
        System.out.println(resume1 + "\n");
        for (Map.Entry<ContactType, String> entry : resume1.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, AbstractSection> entry : resume1.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + "\n" + entry.getValue());
        }
    }

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
}