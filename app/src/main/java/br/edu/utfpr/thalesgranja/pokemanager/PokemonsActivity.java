package br.edu.utfpr.thalesgranja.pokemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PokemonsActivity extends AppCompatActivity {

    private ListView listViewPokemons;
    private List<Pokemon> listPokemons;
    private PokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        setTitle(getString(R.string.pokemon_manager));

        listViewPokemons = findViewById(R.id.listViewPokemons);
        
        listViewPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                Pokemon pokemon = (Pokemon) listViewPokemons.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), getString(R.string.the_pokemon_of_specie) + pokemon.getSpecie() + getString(R.string.was_clicked), Toast.LENGTH_LONG).show();
            }
        });

        populatePokemonList();
    }

    private void populatePokemonList() {
        /*String[] pokemons_species     = getResources().getStringArray(R.array.pokemon_specie);
        String[] pokemons_nicknames   = getResources().getStringArray(R.array.pokemon_nickname);
        int[] pokemons_levels        = getResources().getIntArray(R.array.pokemon_level);
        String[] pokemons_types      = getResources().getStringArray(R.array.pokemon_type);
        int[] pokemons_origins       = getResources().getIntArray(R.array.pokemon_origin);
        int[] pokemons_party         = getResources().getIntArray(R.array.pokemon_party);*/

        listPokemons = new ArrayList<>();

        /*Pokemon pokemon;
        boolean addParty;
        PokemonOrigin pokemonOrigin;

        PokemonOrigin[] pokemonsOrigins = PokemonOrigin.values();

        for(int cont = 0; cont < pokemons_species.length; cont++){
            addParty = (pokemons_party[cont] == 1 ? true : false);

            pokemonOrigin = pokemonsOrigins[pokemons_origins[cont]];

            pokemon = new Pokemon(
                    pokemons_species[cont],
                    pokemons_nicknames[cont],
                    pokemons_levels[cont],
                    pokemons_types[cont],
                    pokemonOrigin,
                    addParty);

            listPokemons.add(pokemon);
        }*/

        pokemonAdapter = new PokemonAdapter(this, listPokemons);

        listViewPokemons.setAdapter(pokemonAdapter);

    }

    public void openAbout(View view) {
        Intent intentAbertura = new Intent(this, AboutActivity.class);

        startActivity(intentAbertura);
    }

    ActivityResultLauncher<Intent> launcherNewPokemon = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == PokemonsActivity.RESULT_OK){
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null){
                            String specie = bundle.getString(PokemonActivity.KEY_SPECIE);
                            String nickname = bundle.getString(PokemonActivity.KEY_NICKNAME);
                            int level = bundle.getInt(PokemonActivity.KEY_LEVEL);
                            String types = bundle.getString(PokemonActivity.KEY_TYPES);
                            String pokeOrigin = bundle.getString(PokemonActivity.KEY_ORIGIN);
                            boolean party = bundle.getBoolean(PokemonActivity.KEY_PARTY);

                            Pokemon pokemon = new Pokemon(specie, nickname, level, types, PokemonOrigin.valueOf(pokeOrigin), party);

                            listPokemons.add(pokemon);

                            pokemonAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
    public void openRegister(View view) {
        Intent intentAbertura = new Intent(this, PokemonActivity.class);

        launcherNewPokemon.launch(intentAbertura);
    }

}