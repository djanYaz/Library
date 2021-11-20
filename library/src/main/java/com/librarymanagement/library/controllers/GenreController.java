package com.librarymanagement.library.controllers;

import com.librarymanagement.library.entities.Genre;
import com.librarymanagement.library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("/app")
public class GenreController {
    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/genres")
    public List<Genre> getListGenres() {
        try {
            return genreRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
            return Arrays.asList();
        }
    }
}
