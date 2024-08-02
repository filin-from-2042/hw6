package com.github.javarar.poke;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api")
public class Controller {

    private final PokemonService pokemonService;

    public Controller(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping("/syncAll")
    public List<Pokemon> getPokemonsSyncAll(@RequestBody List<String> requestedNames) {
        return pokemonService.getByNames(requestedNames);
    }

    @PostMapping("/syncOne")
    public Pokemon getPokemonsSyncOne(@RequestBody List<String> requestedNames) {
        return pokemonService.getByName(requestedNames.stream().findAny().get());
    }

    @PostMapping("/asyncAll")
    public List<Pokemon> getPokemonsAsyncAll(@RequestBody List<String> requestedNames) {
        List<CompletableFuture<Pokemon>> futures = new ArrayList<>();
        for (String name : requestedNames) {
            futures.add(CompletableFuture.supplyAsync(() -> pokemonService.getByName(name)));
        }

        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @PostMapping("/asyncOne")
    public Pokemon getPokemonsAsyncOne(@RequestBody List<String> requestedNames) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Pokemon>> futures = new ArrayList<>();
        for (String name : requestedNames) {
            futures.add(CompletableFuture.supplyAsync(() -> pokemonService.getByName(name)));
        }

        return (Pokemon) CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0])).get();
    }
}
