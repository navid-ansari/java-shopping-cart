package com.shopping_cart.repository.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping_cart.exception.custom.donut.DonutJsonFileNotFoundException;
import com.shopping_cart.model.donut.Donut;
import com.shopping_cart.model.donut.Topping;
import com.shopping_cart.repository.DonutRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DonutRepositoryImpl implements DonutRepository {

    @Override
    public List<Donut> getAllDonuts() throws IOException {
        File file = getJsonFileData();
        ObjectMapper mapper = new ObjectMapper();
        List<Donut> donuts = Arrays.asList(mapper.readValue(file, Donut[].class));
        return donuts;
    }

    /*@Override
    public Donut getDonutById(String donutId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        return donut;
    }

    @Override
    public List<Topping> getToppingsByDonutId(String donutId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        List<Topping> toppingList = donut.topping;
        return toppingList;
    }

    @Override
    public Topping getToppingById(String donutId, String toppingId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        List<Topping> toppingList = donut.topping;
        Topping topping = filterToppingById(toppingList, toppingId);
        return topping;
    }*/

    @Override
    public File getJsonFileData() {
        try {
            File file = new File(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResource("data/donut.json")).getFile()
            );
            return file;
        } catch (Exception e) {
            throw new DonutJsonFileNotFoundException("Donut json file not found");
        }
    }

    /*List<Donut> getDonutList() throws IOException {
        File file = getJsonFileData();
        ObjectMapper mapper = new ObjectMapper();
        List<Donut> donuts = Arrays.asList(mapper.readValue(file, Donut[].class));
        return donuts;
    }

    Donut filterDonutById(List<Donut> donuts, String donutId) {
        Donut donut = donuts.stream().filter(item -> item.getId().equals(donutId)).findFirst().orElse(null);
        return donut;
    }

    Topping filterToppingById(List<Topping> toppings, String toppingId) {
        Topping topping = toppings.stream().filter(item -> item.getId().equals(toppingId)).findFirst().orElse(null);
        return topping;
    }*/
}
