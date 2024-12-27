package com.devstudios.springcloud.msvc.items.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.springcloud.msvc.items.models.Item;
import com.devstudios.springcloud.msvc.items.models.Product;
import com.devstudios.springcloud.msvc.items.services.ItemService;





@RestController
public class ItemController {

    @Autowired
    private ItemService service;

    @Autowired
    private CircuitBreakerFactory factory;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);


    @GetMapping
    public List<Item> list( @RequestParam(name="name", required=false) String name, 
        @RequestHeader(name = "token-request", required = false) String token )
    {
        System.out.println(name);
        System.out.println(token);

        return service.findAll();
    }
    // @GetMapping
    // public List<Item> list( @RequestParam(name="name", required=false) String name, 
    //     @RequestHeader(name = "token-request", required = false) String token )
    // {
    //     System.out.println(name);
    //     System.out.println(token);

    //     return service.findAll();
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Item> details( @PathVariable Long id ){
        // circuito
        Optional<Item> p = factory.create("items").run(() -> service.findById(id),
            (err) -> {
                logger.error(err.getMessage());

                Product product = new Product();
                product.setCreatedAt(LocalDate.now());
                product.setId(1L);
                product.setName("Camara Sony");
                product.setPrice(500.00);

                return Optional.of(new Item(product, 5));
            }
        );

        if( p.isEmpty() ) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(p.get());
    }

}
