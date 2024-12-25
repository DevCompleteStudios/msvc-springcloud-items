package com.devstudios.springcloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devstudios.springcloud.msvc.items.models.Item;
import com.devstudios.springcloud.msvc.items.models.Product;



@Service
public class ItemServiceWebClient implements ItemService {

    @Autowired
    private WebClient.Builder client;


    @Override
    public List<Item> findAll() {
        return client.build()
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Product.class)
            .map( p -> new Item(p, 1) )
            .collectList()
            .block();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try {
            return Optional.of(
                client.build()
                .get()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .map( p -> new Item(p, 1) )
                .block()
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
