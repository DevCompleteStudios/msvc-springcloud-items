package com.devstudios.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devstudios.springcloud.msvc.items.clients.ProductFeignClient;
import com.devstudios.springcloud.msvc.items.models.Item;
import com.devstudios.springcloud.msvc.items.models.Product;

import feign.FeignException;




@Service
public class ItemServiceFeign implements ItemService {

    @Autowired
    ProductFeignClient client;
    private final Random random = new Random();


    @Override
    public List<Item> findAll() {
        return client.findAll()
            .stream()
            .map( p -> new Item(p, random.nextInt(10) + 1) )
            .toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = client.details(id);
            return Optional.of(
                new Item(product, random.nextInt(10) + 1)
            );
        } catch (FeignException e) {
            return Optional.empty();
        }
    }



}
