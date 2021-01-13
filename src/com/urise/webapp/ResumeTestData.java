package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {

        Resume resume1 = new Resume("uuid1", "Roman Tonkikh");
        resume1.setContact(ContactType.PHONE, "999-8888-9999");
        resume1.setContact(ContactType.SKYPE, "romanSkype");
        resume1.setContact(ContactType.EMAIL, "roman@Email");
        resume1.setContact(ContactType.LINKEDIN, "romanLinkedin");
        resume1.setContact(ContactType.GITHUB, "romanGithub");
        resume1.setContact(ContactType.STACKOVERFLOW, "romanStackoverflow");
        resume1.setContact(ContactType.HOMEPAGE, "romanHomepage");

        TextSection sectionObjective = new TextSection("Ученик");
        TextSection sectionPersonal = new TextSection("Трудолюбие, целеустремленность, активность, коммуникабельность");

        List<String> listRomanAchievement = Arrays.asList("Учусь", "Учусь", "Учусь");
        ListSection listAchievement = new ListSection(listRomanAchievement);

        List<String> listRomanQualifications = Arrays.asList("Java syntax", "Java core");
        ListSection listQualifications = new ListSection(listRomanQualifications);

        Position position1 = new Position("JavaRush", "https://javarush.ru", LocalDate.of(2020, 8, 1), LocalDate.of(2020, 11, 10), "Ученик", "Изучал Java syntax, core, дошел до 22 уровня");
        Position position2 = new Position("Java Online Projects", "https://javaops.ru/", LocalDate.of(2020, 11, 11), LocalDate.now(), "Ученик", "Изучаю Java на примере создания web приложения");
        Company companyExperience = new Company(Arrays.asList(position1, position2));
        Company companyEducation = new Company(Arrays.asList(position1, position2));

        resume1.setSection(SectionType.OBJECTIVE, sectionObjective);
        resume1.setSection(SectionType.PERSONAL, sectionPersonal);
        resume1.setSection(SectionType.ACHIEVEMENT, listAchievement);
        resume1.setSection(SectionType.QUALIFICATIONS, listQualifications);
        resume1.setSection(SectionType.EXPERIENCE, companyExperience);
        resume1.setSection(SectionType.EDUCATION, companyEducation);

        System.out.println(resume1 + "\n");
        for (Map.Entry<ContactType, String> entry : resume1.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, AbstractSection> entry : resume1.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + "\n" + entry.getValue());
        }
    }
}