package com.devstudios.springcloud.msvc.items.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devstudios.springcloud.msvc.items.models.Item;
import com.devstudios.springcloud.msvc.items.services.ItemService;





@RestController
public class ItemController {

    @Autowired
    private ItemService service;


    @GetMapping
    public List<Item> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> details( @PathVariable Long id ){
        Optional<Item> p = service.findById(id);
        if( p.isEmpty() ) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(p.get());
    }

}
