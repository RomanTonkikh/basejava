package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements Serializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            nextgenWriter(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            nextgenWriter(dos, resume.getSections().entrySet(), entry -> {
                SectionType st = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(st.name());
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        nextgenWriter(dos, ((ListSection) section).getTextList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        nextgenWriter(dos, ((OrganizationSection) section).getListOrganization(), organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl() == null ? "" : organization.
                                    getHomePage().getUrl());
                            nextgenWriter(dos, organization.getPositions(), position -> {
                                dos.writeInt(position.getStartDate().getYear());
                                dos.writeInt(position.getStartDate().getMonth().getValue());
                                dos.writeInt(position.getEndDate().getYear());
                                dos.writeInt(position.getEndDate().getMonth().getValue());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription() == null ? "" : position.getDescription());
                            });
                        });
                        break;
                    default:
                        break;
                }
            });

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
            int sectionSize = dis.readInt();
            for (int count = 0; count < sectionSize; count++) {
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
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            Link link = new Link(name, url.equals("") ? null : url);
                            List<Organization.Position> listPos = new ArrayList<>();
                            int posSize = dis.readInt();
                            for (int j = 0; j < posSize; j++) {
                                LocalDate StatDate = LocalDate.of(dis.readInt(), dis.readInt(), 1);
                                LocalDate EndDate = LocalDate.of(dis.readInt(), dis.readInt(), 1);
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                Organization.Position position = new Organization.Position(StatDate, EndDate, title,
                                        description.equals("") ? null : description);
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

    private <X> void nextgenWriter(DataOutputStream dos,
                                   Collection<X> collection, Writer<X> writer) throws IOException {
        dos.writeInt(collection.size());
        for (X item : collection) {
            writer.write(item);
        }
    }
}
