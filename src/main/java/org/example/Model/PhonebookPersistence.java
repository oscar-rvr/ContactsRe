package org.example.Model;

import java.io.*;
import java.util.List;

public class PhonebookPersistence
{
    private File file;

    public PhonebookPersistence(File file)
    {
        this.file = file;
    }

    public void save(List<AbstractRecord> contacts, File file)
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(contacts);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AbstractRecord> load(File file)
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            return (List<AbstractRecord>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
