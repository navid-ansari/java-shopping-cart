package com.shopping_cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping_cart.dto.donut.DonutResponseDTO;
import com.shopping_cart.dto.donut.ToppingResponseDTO;
import com.shopping_cart.model.donut.Donut;
import com.shopping_cart.model.donut.Topping;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DonutService {

    public List<DonutResponseDTO> getAllDonuts() throws IOException {
        //List<DonutResponseDTO> fileData = mapper.readValue(file, new TypeReference<List<DonutResponseDTO>>(){});
        //List<DonutResponseDTO> fileData = mapper.readValue(file, List.class);
        List<Donut> donuts = getDonutList();
        return donuts.stream().map(this::mapDonutEntityToDto).collect(Collectors.toList());
    }

    public DonutResponseDTO getDonutById(String donutId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        return mapDonutEntityToDto(donut);
    }

    public List<ToppingResponseDTO> getToppingsByDonutId(String donutId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        List<Topping> toppingList = donut.topping;
        return toppingList.stream().map(this::mapToppingEntityToDto).collect(Collectors.toList());
    }

    public ToppingResponseDTO getToppingById(String donutId, String toppingId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        List<Topping> toppingList = donut.topping;
        Topping topping = filterToppingById(toppingList, toppingId);
        return mapToppingEntityToDto(topping);
    }

    File getJsonFile() {
        File file = new File(
                Objects.requireNonNull(this.getClass().getClassLoader().getResource("data/donut.json")).getFile()
        );
        return file;
    }

    List<Donut> getDonutList() throws IOException {
        File file = getJsonFile();
        ObjectMapper mapper = new ObjectMapper();
        List<Donut> donuts = Arrays.asList(mapper.readValue(file, Donut[].class));
        return donuts;
    }

    Donut filterDonutById(List<Donut> donuts, String donutId){
        Donut donut = donuts.stream().filter(item -> item.getId().equals(donutId)).findFirst().orElse(null);
        return donut;
    }

    Topping filterToppingById(List<Topping> toppings, String toppingId){
        Topping topping = toppings.stream().filter(item -> item.getId().equals(toppingId)).findFirst().orElse(null);
        return topping;
    }

    DonutResponseDTO mapDonutEntityToDto(Donut donut){
        DonutResponseDTO donutResponseDTO = new DonutResponseDTO();
        ModelMapper modelMapper = new ModelMapper();
        donutResponseDTO = modelMapper.map(donut, DonutResponseDTO.class);
        return donutResponseDTO;
    }

    ToppingResponseDTO mapToppingEntityToDto(Topping topping){
        ToppingResponseDTO toppingResponseDTO = new ToppingResponseDTO();
        ModelMapper modelMapper = new ModelMapper();
        toppingResponseDTO = modelMapper.map(topping, ToppingResponseDTO.class);
        return toppingResponseDTO;
    }

}
