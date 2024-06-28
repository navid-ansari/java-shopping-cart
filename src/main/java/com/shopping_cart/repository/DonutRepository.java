package com.shopping_cart.repository;

import com.shopping_cart.model.donut.Donut;
import com.shopping_cart.model.donut.Topping;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public interface DonutRepository {

    File getJsonFileData();
    
    List<Donut> getAllDonuts() throws IOException;

    //Donut getDonutById(String donutId) throws IOException;

    //List<Topping> getToppingsByDonutId(String donutId) throws IOException;

    //Topping getToppingById(String donutId, String toppingId) throws IOException;


}
