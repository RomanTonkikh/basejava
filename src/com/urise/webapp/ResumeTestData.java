package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

        TextSection sectionObjective = new TextSection("Ученик");
        TextSection sectionPersonal = new TextSection("Трудолюбие, целеустремленность, активность, коммуникабельность");

        List<String> listRomanAchievement = Arrays.asList("Учусь", "Учусь", "Учусь");
        ListSection listAchievement = new ListSection(listRomanAchievement);

        List<String> listRomanQualifications = Arrays.asList("Java syntax", "Java core");
        ListSection listQualifications = new ListSection(listRomanQualifications);

        Organization organization1 = new Organization("JavaRush", "https://javarush.ru", LocalDate.of(2020, 8, 1), LocalDate.of(2020, 11, 10), "Ученик", "Изучал Java syntax, core, дошел до 22 уровня");
        Organization organization2 = new Organization("Java Online Projects", "https://javaops.ru/", LocalDate.of(2020, 11, 11), LocalDate.now(), "Ученик", "Изучаю Java на примере создания web приложения");
        organization1.getPeriods().add(new Position(LocalDate.of(2021, 1, 1), LocalDate.now(), "Ученик", "Продолжаю решать задачи"));
        CompanySection companyExperience = new CompanySection(Arrays.asList(organization1, organization2));
        CompanySection companyEducation = new CompanySection(Arrays.asList(organization1, organization2));

        resume.setSection(SectionType.OBJECTIVE, sectionObjective);
        resume.setSection(SectionType.PERSONAL, sectionPersonal);
        resume.setSection(SectionType.ACHIEVEMENT, listAchievement);
        resume.setSection(SectionType.QUALIFICATIONS, listQualifications);
        resume.setSection(SectionType.EXPERIENCE, companyExperience);
        resume.setSection(SectionType.EDUCATION, companyEducation);
        return resume;
    }
}