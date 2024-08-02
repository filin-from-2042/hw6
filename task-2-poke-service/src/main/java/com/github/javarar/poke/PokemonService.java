package com.github.javarar.poke;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PokemonService {
    Pokemon getByName(@NotNull String name);
    List<Pokemon> getByNames(@NotNull List<String> names);
}
