package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            /*dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }*/
            nextgenWriter(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                AbstractSection section = entry.getValue();
                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> textList = ((ListSection) section).getTextList();
                        dos.writeInt(textList.size());
                        for (String str : textList) {
                            dos.writeUTF(str);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> listOrganization = ((OrganizationSection) section).getListOrganization();
                        dos.writeInt(listOrganization.size());
                        for (Organization organization : listOrganization) {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            dos.writeInt(organization.getPositions().size());
                            for (Organization.Position position : organization.getPositions()) {
                                dos.writeInt(position.getStartDate().getYear());
                                dos.writeInt(position.getStartDate().getMonth().getValue());
                                dos.writeInt(position.getEndDate().getYear());
                                dos.writeInt(position.getEndDate().getMonth().getValue());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            int size = dis.readInt();
            Resume resume = new Resume(uuid, fullName);
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            while (dis.available() > 0) {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < listSize; i++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(st, new ListSection(list));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int orgSize = dis.readInt();
                        for (int i = 0; i < orgSize; i++) {
                            Link link = new Link(dis.readUTF(), dis.readUTF());
                            List<Organization.Position> listPos = new ArrayList<>();
                            int posSize = dis.readInt();
                            for (int j = 0; j < posSize; j++) {
                                Organization.Position position = new Organization.Position(LocalDate.of(dis.readInt(),
                                        dis.readInt(), 1), LocalDate.of(dis.readInt(), dis.readInt(), 1), dis.readUTF(), dis.readUTF());
                                listPos.add(position);
                            }
                            resume.addSection(st, new OrganizationSection(new Organization(link, listPos)));
                        }
                        break;
                    default:
                        break;
                }
            }
            return resume;
        }
    }

    interface Writer<X> {
        void write(X x) throws IOException;
    }

    private <X> void nextgenWriter(DataOutputStream dos, Collection<X> collection, Writer<X> writer) throws IOException {
        dos.writeInt(collection.size());
        for (X item : collection) {
            writer.write(item);

        }
    }
}
