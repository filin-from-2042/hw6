package com.github.javarar.poke;

import java.util.List;

public class PokemonResponseDto {
    private final Integer count;
    private final String next;
    private final String previous;
    private final List<Pokemon> results;

    public PokemonResponseDto(Integer count, String next, String previous, List<Pokemon> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Pokemon> getResults() {
        return results;
    }
}
