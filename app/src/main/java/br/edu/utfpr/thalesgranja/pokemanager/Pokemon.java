package br.edu.utfpr.thalesgranja.pokemanager;

import java.util.Comparator;

public class Pokemon {

    public static Comparator<Pokemon> ascendingOrder = new Comparator<Pokemon>() {
        @Override
        public int compare(Pokemon pokemon1, Pokemon pokemon2) {
            return pokemon1.getSpecie().compareToIgnoreCase(pokemon2.getSpecie());
        }
    };

    private String specie;
    private String nickname;
    private int level;
    private String type;
    private PokemonOrigin pokemonOrigin;
    private boolean addParty;

    public Pokemon(String specie, String nickname, int level, String type, PokemonOrigin pokemonOrigin, boolean addParty) {
        this.specie = specie;
        this.nickname = nickname;
        this.level = level;
        this.type = type;
        this.pokemonOrigin = pokemonOrigin;
        this.addParty = addParty;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PokemonOrigin getPokemonOrigin() {
        return pokemonOrigin;
    }

    public void setPokemonOrigin(PokemonOrigin pokemonOrigin) {
        this.pokemonOrigin = pokemonOrigin;
    }

    public boolean isAddParty() {
        return addParty;
    }

    public void setAddParty(boolean addParty) {
        this.addParty = addParty;
    }
}
