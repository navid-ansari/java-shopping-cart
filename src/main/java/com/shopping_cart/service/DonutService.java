package com.shopping_cart.service;

import com.shopping_cart.dto.donut.DonutResponseDTO;
import com.shopping_cart.dto.donut.ToppingResponseDTO;
import com.shopping_cart.exception.custom.donut.DonutNotFoundException;
import com.shopping_cart.exception.custom.donut.ToppingNotFoundException;
import com.shopping_cart.model.donut.Donut;
import com.shopping_cart.model.donut.Topping;
import com.shopping_cart.repository.DonutRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DonutService {

    private final DonutRepository donutRepository;

    public List<DonutResponseDTO> allDonuts() throws IOException {
        List<Donut> donutList = donutRepository.getAllDonuts();
        return donutList.stream().map(this::mapDonutEntityToDto).collect(Collectors.toList());
    }

    public DonutResponseDTO donutById(String donutId) throws IOException {
        List<Donut> donutList = donutRepository.getAllDonuts();
        Donut donut = filterDonutById(donutList, donutId);
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        return mapDonutEntityToDto(donut);
    }

    public List<ToppingResponseDTO> toppingsByDonutId(String donutId) throws IOException {
        List<Donut> donutList = donutRepository.getAllDonuts();
        Donut donut = filterDonutById(donutList, donutId);
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        return donut.topping.stream().map(this::mapToppingEntityToDto).collect(Collectors.toList());

        /*List<Topping> toppings = donutRepository.getToppingsByDonutId(donutId);
        return toppings.stream().map(this::mapToppingEntityToDto).collect(Collectors.toList());*/

        /*List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        // if donut not found
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        //if (donut.topping == null) {
        //    throw new ToppingNotAvailableException("Topping not available for donut id " + donutId + " ");
        //}
        List<Topping> toppingList = donut.topping;
        return toppingList.stream().map(this::mapToppingEntityToDto).collect(Collectors.toList());*/
    }

    public ToppingResponseDTO toppingById(String donutId, String toppingId) throws IOException {

        List<Donut> donutList = donutRepository.getAllDonuts();
        Donut donut = filterDonutById(donutList, donutId);
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        Topping topping = filterToppingById(donut.topping, toppingId);
        if (topping == null) {
            throw new ToppingNotFoundException("Topping with id " + toppingId + " not found");
        }
        return mapToppingEntityToDto(topping);

        /*Topping topping = this.donutRepository.getToppingById(donutId, toppingId);
        return mapToppingEntityToDto(topping);*/

        /*List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        // if donut not found
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        List<Topping> toppingList = donut.topping;
        Topping topping = filterToppingById(toppingList, toppingId);
        // if topping not found
        if (topping == null) {
            throw new ToppingNotFoundException("Topping with id " + toppingId + " not found");
        }
        return mapToppingEntityToDto(topping);*/
    }
    /*public List<DonutResponseDTO> getAllDonuts() throws IOException {
        List<Donut> donuts = getDonutList();
        return donuts.stream().map(this::mapDonutEntityToDto).collect(Collectors.toList());
    }

    public DonutResponseDTO getDonutById(String donutId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        // if donut not found
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        return mapDonutEntityToDto(donut);
    }

    public List<ToppingResponseDTO> getToppingsByDonutId(String donutId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        // if donut not found
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        //if (donut.topping == null) {
        //    throw new ToppingNotAvailableException("Topping not available for donut id " + donutId + " ");
        //}
        List<Topping> toppingList = donut.topping;
        return toppingList.stream().map(this::mapToppingEntityToDto).collect(Collectors.toList());
    }

    public ToppingResponseDTO getToppingById(String donutId, String toppingId) throws IOException {
        List<Donut> donuts = getDonutList();
        Donut donut = filterDonutById(donuts, donutId);
        // if donut not found
        if (donut == null) {
            throw new DonutNotFoundException("Donut with id " + donutId + " not found");
        }
        List<Topping> toppingList = donut.topping;
        Topping topping = filterToppingById(toppingList, toppingId);
        // if topping not found
        if (topping == null) {
            throw new ToppingNotFoundException("Topping with id " + toppingId + " not found");
        }
        return mapToppingEntityToDto(topping);
    }*/

    Donut filterDonutById(List<Donut> donutList, String donutId) throws IOException {
        Donut donut = donutList.stream().filter(item -> item.getId().equals(donutId)).findFirst().orElse(null);
        return donut;
    }

    Topping filterToppingById(List<Topping> toppings, String toppingId) {
        Topping topping = toppings.stream().filter(item -> item.getId().equals(toppingId)).findFirst().orElse(null);
        return topping;
    }

    /*File getJsonFile() {
        return this.donutRepository.getJsonFileData();
    }

    List<Donut> getDonutList() throws IOException {
        File file = getJsonFile();
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

    DonutResponseDTO mapDonutEntityToDto(Donut donut) {
        DonutResponseDTO donutResponseDTO = new DonutResponseDTO();
        ModelMapper modelMapper = new ModelMapper();
        donutResponseDTO = modelMapper.map(donut, DonutResponseDTO.class);
        return donutResponseDTO;
    }

    ToppingResponseDTO mapToppingEntityToDto(Topping topping) {
        ToppingResponseDTO toppingResponseDTO = new ToppingResponseDTO();
        ModelMapper modelMapper = new ModelMapper();
        toppingResponseDTO = modelMapper.map(topping, ToppingResponseDTO.class);
        return toppingResponseDTO;
    }

}
