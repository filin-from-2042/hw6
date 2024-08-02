package com.github.javarar.poke;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService{

    private final RestTemplate restTemplate;

    public PokemonServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Pokemon> getByNames(@NotNull List<String> names) {
        return names.stream().map(this::getByName).collect(Collectors.toList());
    }

    @Override
    public Pokemon getByName(@NotNull String name) {
        Objects.requireNonNull(name);

        System.out.println("Getting " + name);
        ResponseEntity<Pokemon> pokemon = restTemplate.getForEntity(String.format("https://pokeapi.co/api/v2/pokemon/%s", name), Pokemon.class);

        return pokemon.getBody();
    }
}
