package com.cerberus.register;

import com.cerberus.input.selection.SelectionItem;
import com.cerberus.input.selection.SelectionOption;
import com.cerberus.models.helpers.GsonHelper;
import com.cerberus.models.motorcycle.Motorcycle;
import com.google.gson.Gson;

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
    public static MotorcyclesRegister fromFile(File file) throws FileNotFoundException {
        MotorcyclesRegister register = new MotorcyclesRegister(file);

        Gson gson = GsonHelper.create();

        // load customers list from json data file
        Motorcycle[] cArray = gson.fromJson(new FileReader(file), Motorcycle[].class);

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
    public void addCustomer(Motorcycle motorcycle) {
        addCustomer(motorcycle, true);
    }

    /**
     * adds new customer
     * removes this customer if storage cant be updated
     * @param motorcycle to add
     * @param update whether to check if storage can be updated if so do so
     */
    public void addCustomer(Motorcycle motorcycle, boolean update) {
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

    public List<SelectionOption> toSelectionList() {

        return getMotorcycles()
                .stream()
                .map(motorcycle -> SelectionOption.create(motorcycle.getName(), (index) -> {}))
                .collect(Collectors.toList());

    }

}