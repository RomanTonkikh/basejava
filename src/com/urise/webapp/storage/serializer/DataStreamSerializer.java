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
                            final Link homePage = organization.getHomePage();
                            dos.writeUTF(homePage.getName());
                            final String url = homePage.getUrl();
                            dos.writeUTF(url == null ? "" : url);
                            nextgenWriter(dos, organization.getPositions(), position -> {
                                dateWriter(dos, position.getStartDate());
                                dateWriter(dos, position.getEndDate());
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

    private void dateWriter(DataOutputStream dos, LocalDate locDate) throws IOException {
        dos.writeInt(locDate.getYear());
        dos.writeInt(locDate.getMonth().getValue());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            nextgenReader(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            nextgenReader(dis, () -> {
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
                                LocalDate StatDate = dateReader(dis);
                                LocalDate EndDate = dateReader(dis);
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

            });
            return resume;
        }
    }

    private LocalDate dateReader(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    interface Writer<X> {
        void write(X x) throws IOException;
    }

    interface Reader {
        void read() throws IOException;
    }

    private <X> void nextgenWriter(DataOutputStream dos,
                                   Collection<X> collection, Writer<X> writer) throws IOException {
        dos.writeInt(collection.size());
        for (X item : collection) {
            writer.write(item);
        }

    }

    private void nextgenReader(DataInputStream dis, Reader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }
}
