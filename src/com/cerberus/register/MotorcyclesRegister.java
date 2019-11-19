package com.cerberus.register;

import com.cerberus.models.motorcycle.Motorcycle;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    private Gson gson = new Gson();

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

        Gson gson = new Gson();

        // load customers list from json data file
        Motorcycle[] cArray = gson.fromJson(new FileReader(file), Motorcycle[].class);
        register.setMotorcycles(new ArrayList<>(Arrays.asList(cArray)));

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
    private void updateStorage() throws IOException {

        String json = gson.toJson(this.getMotorcycles().toArray());

        new FileWriter(storage) {{
            write(json);
        }}.close();

    }

}
