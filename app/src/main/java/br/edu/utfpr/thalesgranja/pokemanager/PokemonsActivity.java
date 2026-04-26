package br.edu.utfpr.thalesgranja.pokemanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokemonsActivity extends AppCompatActivity {

    private ListView listViewPokemons;
    private List<Pokemon> listPokemons;
    private PokemonAdapter pokemonAdapter;

    private int positionEdit = -1;
    private ActionMode actionMode;
    private View viewSelecionada;
    private Drawable backgroundDrawable;

    private ActionMode.Callback actionCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.pokemons_item_selected, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEdit){
                editPokemon();
                return true;
            } else if (idMenuItem == R.id.menuItemDelete){
                deletePokemon();
                mode.finish();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode         = null;
            viewSelecionada    = null;
            backgroundDrawable = null;

            listViewPokemons.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        setTitle(getString(R.string.pokemon_manager));

        listViewPokemons = findViewById(R.id.listViewPokemons);

        populatePokemonList();

    }

    private void populatePokemonList() {
        listPokemons = new ArrayList<>();
        pokemonAdapter = new PokemonAdapter(this, listPokemons);

        listViewPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode == null) {
                    positionEdit = position;
                    editPokemon();
                }
            }
        });

        listViewPokemons.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (actionMode != null) {
                    return false;
                }

                positionEdit = position;
                viewSelecionada = view;
                backgroundDrawable = view.getBackground();

                view.setBackgroundColor(Color.LTGRAY);

                listViewPokemons.setEnabled(false);

                actionMode = startSupportActionMode(actionCallback);

                return true;
            }
        });

        listViewPokemons.setAdapter(pokemonAdapter);
    }

    public void openAbout() {
        Intent intentAbertura = new Intent(this, AboutActivity.class);
        startActivity(intentAbertura);
    }

    ActivityResultLauncher<Intent> launcherNewPokemon = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == PokemonsActivity.RESULT_OK){
                        Intent intent = result.getData();
                        Bundle bundle = intent != null ? intent.getExtras() : null;

                        if (bundle != null){
                            String specie = bundle.getString(PokemonActivity.KEY_SPECIE);
                            String nickname = bundle.getString(PokemonActivity.KEY_NICKNAME);
                            int level = bundle.getInt(PokemonActivity.KEY_LEVEL);
                            String types = bundle.getString(PokemonActivity.KEY_TYPES);
                            String pokeOrigin = bundle.getString(PokemonActivity.KEY_ORIGIN);
                            boolean party = bundle.getBoolean(PokemonActivity.KEY_PARTY);

                            Pokemon pokemon = new Pokemon(specie, nickname, level, types, PokemonOrigin.valueOf(pokeOrigin), party);

                            listPokemons.add(pokemon);

                            Collections.sort(listPokemons, Pokemon.ascendingOrder);

                            pokemonAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

    public void openRegister() {
        Intent intentAbertura = new Intent(this, PokemonActivity.class);
        launcherNewPokemon.launch(intentAbertura);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokemons_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemRegister){
            openRegister();
            return true;
        } else if (idMenuItem == R.id.menuItemAbout) {
            openAbout();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void deletePokemon(){
        listPokemons.remove(positionEdit);
        pokemonAdapter.notifyDataSetChanged();
    }

    ActivityResultLauncher<Intent> launcherEditPokemon = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == PokemonsActivity.RESULT_OK){
                        Intent intent = result.getData();
                        Bundle bundle = intent != null ? intent.getExtras() : null;

                        if (bundle != null && positionEdit != -1){
                            String specie = bundle.getString(PokemonActivity.KEY_SPECIE);
                            String nickname = bundle.getString(PokemonActivity.KEY_NICKNAME);
                            int level = bundle.getInt(PokemonActivity.KEY_LEVEL);
                            String types = bundle.getString(PokemonActivity.KEY_TYPES);
                            String pokeOrigin = bundle.getString(PokemonActivity.KEY_ORIGIN);
                            boolean party = bundle.getBoolean(PokemonActivity.KEY_PARTY);

                            Pokemon pokemon = listPokemons.get(positionEdit);
                            pokemon.setSpecie(specie);
                            pokemon.setNickname(nickname);
                            pokemon.setLevel(level);
                            pokemon.setType(types);
                            pokemon.setPokemonOrigin(PokemonOrigin.valueOf(pokeOrigin));
                            pokemon.setAddParty(party);

                            Collections.sort(listPokemons, Pokemon.ascendingOrder);

                            pokemonAdapter.notifyDataSetChanged();
                        }
                    }

                    positionEdit = -1;

                    if (actionMode != null){
                        actionMode.finish();
                    }
                }
            });

    private void editPokemon(){
        Pokemon pokemon = listPokemons.get(positionEdit);

        Intent intentAbertura = new Intent(this, PokemonActivity.class);

        intentAbertura.putExtra(PokemonActivity.KEY_SPECIE, pokemon.getSpecie());
        intentAbertura.putExtra(PokemonActivity.KEY_NICKNAME, pokemon.getNickname());
        intentAbertura.putExtra(PokemonActivity.KEY_LEVEL, pokemon.getLevel());
        intentAbertura.putExtra(PokemonActivity.KEY_TYPES, pokemon.getType());
        intentAbertura.putExtra(PokemonActivity.KEY_ORIGIN, pokemon.getPokemonOrigin().toString());
        intentAbertura.putExtra(PokemonActivity.KEY_PARTY, pokemon.isAddParty());

        launcherEditPokemon.launch(intentAbertura);
    }
}