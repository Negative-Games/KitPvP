package dev.negativekb.kitpvpframework.core.cache;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ObjectCache<T> {

    private final String path;
    private final Gson gson;
    private final Class<T[]> clazz;

    public ObjectCache(String path, Class<T[]> clazz) {
        this.path = path;
        this.clazz = clazz;
        gson = new Gson();
    }

    public void save(ArrayList<T> cacheArrayList) throws IOException {
        File file = getFile(path);
        file.getParentFile().mkdir();
        file.createNewFile();

        Writer writer = new FileWriter(file, false);
        gson.toJson(cacheArrayList, writer);
        writer.flush();
        writer.close();
    }


    public ArrayList<T> load() throws IOException {
        File file = getFile(path);
        if (file.exists()) {
            Reader reader = new FileReader(file);
            T[] p = gson.fromJson(reader, clazz);
            return new ArrayList<>(Arrays.asList(p));
        }
        return new ArrayList<>();
    }

    private File getFile(String path) {
        return new File(path);
    }
}