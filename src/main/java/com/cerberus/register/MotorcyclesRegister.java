package com.cerberus.register;

import com.cerberus.input.selection.SelectionOption;
import com.cerberus.models.helpers.GsonHelper;
import com.cerberus.models.helpers.StringHelper;
import com.cerberus.models.helpers.string.SidedLine;
import com.cerberus.models.motorcycle.Motorcycle;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MotorcyclesRegister {

    private ArrayList<Motorcycle> motorcycles;
    public ArrayList<Motorcycle> getMotorcycles() {
        return motorcycles;
    }
    public void setMotorcycles(ArrayList<Motorcycle> motorcycles) {
        this.motorcycles = motorcycles;
    }

    private final File storage;
    public File getStorage() {
        return storage;
    }

    private Gson gson = GsonHelper.create();

    private MotorcyclesRegister(File storage) {
        this.storage = storage;
        this.motorcycles = new ArrayList<>();
    }

    /**
     * create Register with file
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static MotorcyclesRegister fromFile(File file) throws IOException {
        MotorcyclesRegister register = new MotorcyclesRegister(file);

        Gson gson = GsonHelper.create();

        // load customers list from json data file
        Motorcycle[] cArray = new Motorcycle[0];
        try {
            cArray = gson.fromJson(new FileReader(file), Motorcycle[].class);
        } catch (JsonSyntaxException | IOException e) {
            GsonHelper.createArrayFile(file);
            cArray = gson.fromJson(new FileReader(file), Motorcycle[].class);
        }
        try {
            register.setMotorcycles(new ArrayList<>(Arrays.asList(cArray)));
        } catch (NullPointerException ignored) {
            register.setMotorcycles(new ArrayList<>());
        }

        return register;
    }


    /**
     * adds new customer
     * removes this customer if storage cant be updated
     * @param motorcycle to add
     */
    public void addMotorcycle(Motorcycle motorcycle) {
        addMotorcycle(motorcycle, true);
    }

    /**
     * adds new customer
     * removes this customer if storage cant be updated
     * @param motorcycle to add
     * @param update whether to check if storage can be updated if so do so
     */
    public void addMotorcycle(Motorcycle motorcycle, boolean update) {
        // add
        this.getMotorcycles().add(motorcycle);

        if (update) {
            try {
                // update storage
                this.updateStorage();
            } catch (IOException e) {
                System.out.println(e);

                // remove
                this.getMotorcycles().remove(this.getMotorcycles().size() - 1);
            }
        }
    }

    /**
     * overwrite current state to storage
     * @throws IOException
     */
    public void updateStorage() throws IOException {

        String json = gson.toJson(this.getMotorcycles().toArray());

        new FileWriter(storage) {{
            write(json);
        }}.close();

    }

    /**
     * overwrite current state to storage
     * ignores IO exceptions
     */
    public void updateStorageIgnored() {
        try {
            this.updateStorage();
        } catch (IOException ignored) {
//            e.printStackTrace();
        }
    }

    public List<SelectionOption> toSelectionList() {

        return getMotorcycles()
                .stream()
                .map(motorcycle -> SelectionOption.create(motorcycle.getName(), (index) -> {}))
                .collect(Collectors.toList());

    }

    public String registryDetail() {
        StringBuilder builder = new StringBuilder();

        int width = StringHelper.width;

        String separator = StringHelper.create("-", width);

        builder.append(separator).append("\n");

        builder.append(
                new SidedLine(width, "DETAILS", "")
        );

        builder.append(separator).append("\n");

        ArrayList<Motorcycle> motorcycleArrayList = this.getMotorcycles();
        for (int i = 0; i < motorcycleArrayList.size(); i++) {
            Motorcycle motorcycle = motorcycleArrayList.get(i);

            builder.append(
                    new SidedLine(width, motorcycle.getName(),"[ "+ i +" ]")
            );

        }

        builder.append(separator).append("\n");
        builder.append(separator).append("\n");

        return builder.toString();

    }

}
