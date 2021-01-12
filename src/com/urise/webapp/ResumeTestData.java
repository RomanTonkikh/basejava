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

        Company company1 = new Company("JavaRush", "https://javarush.ru", LocalDate.of(2020, 8, 1), LocalDate.of(2020, 11, 10), "Ученик", "Изучал Java syntax, core, дошел до 22 уровня");
        Company company2 = new Company("Java Online Projects", "https://javaops.ru/", LocalDate.of(2020, 11, 11), LocalDate.now(), "Ученик", "Изучаю Java на примере создания web приложения");
        CompanySection companyExperience = new CompanySection(Arrays.asList(company1, company2));
        CompanySection companyEducation = new CompanySection(Arrays.asList(company1, company2));

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
        for (Map.Entry<SectionType, Section> entry : resume1.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + "\n");
            switch (entry.getKey()) {
                case OBJECTIVE:
                    System.out.println(((TextSection) resume1.getSection(SectionType.OBJECTIVE)).getTextField() + "\n");
                    break;
                case PERSONAL:
                    System.out.println(((TextSection) resume1.getSection(SectionType.PERSONAL)).getTextField() + "\n");
                    break;
                case ACHIEVEMENT:
                    for (String s : ((ListSection) resume1.getSection(SectionType.ACHIEVEMENT)).getTextList()) {
                        System.out.println(s + "\n");
                    }
                    break;
                case QUALIFICATIONS:
                    for (String s : ((ListSection) resume1.getSection(SectionType.QUALIFICATIONS)).getTextList()) {
                        System.out.println(s + "\n");
                    }
                    break;
                case EXPERIENCE:
                    for (Company c : ((CompanySection) resume1.getSection(SectionType.EXPERIENCE)).getListCompany()) {
                        System.out.println(c + "\n");
                    }
                    break;
                case EDUCATION:
                    for (Company c : ((CompanySection) resume1.getSection(SectionType.EDUCATION)).getListCompany()) {
                        System.out.println(c + "\n");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}