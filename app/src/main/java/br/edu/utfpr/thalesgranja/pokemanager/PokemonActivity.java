package br.edu.utfpr.thalesgranja.pokemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PokemonActivity extends AppCompatActivity {

    public static final String KEY_SPECIE = "KEY_SPECIE";
    public static final String KEY_NICKNAME = "KEY_NICKNAME";
    public static final String KEY_LEVEL = "KEY_LEVEL";
    public static final String KEY_TYPES = "KEY_TYPES";
    public static final String KEY_ORIGIN = "KEY_ORIGIN";
    public static final String KEY_PARTY = "KEY_PARTY";
    private EditText editTextSpecie, editTextNickname, editTextLevel;
    private Spinner spinnerPrimaryType, spinnerSecondaryType;
    private RadioGroup radioGroupOrigin;
    private CheckBox checkBoxAddParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.register_new_pokemon));

        editTextSpecie = findViewById(R.id.editTextSpecie);
        editTextNickname = findViewById(R.id.editTextNickname);
        editTextLevel = findViewById(R.id.editTextLevel);
        spinnerPrimaryType = findViewById(R.id.spinnerPrimaryType);
        spinnerSecondaryType = findViewById(R.id.spinnerSecondaryType);
        radioGroupOrigin = findViewById(R.id.radioGroupOrigin);
        checkBoxAddParty = findViewById(R.id.checkBoxAddParty);
    }

    public void clearFields(){
        editTextSpecie.setText(null);
        editTextNickname.setText(null);
        editTextLevel.setText(null);
        spinnerPrimaryType.setSelection(0);
        spinnerSecondaryType.setSelection(0);
        radioGroupOrigin.clearCheck();
        checkBoxAddParty.setChecked(false);
        
        editTextSpecie.requestFocus();

        Toast.makeText(this, R.string.the_fields_were_cleared, Toast.LENGTH_LONG).show();
    }

    public void saveValues(){

        String specie = editTextSpecie.getText().toString();

        if(specie ==  null || specie.trim().isEmpty()){
            Toast.makeText(this, R.string.invalid_specie_try_again, Toast.LENGTH_LONG).show();

            editTextSpecie.setText(null);
            editTextSpecie.requestFocus();

            return;
        }

        specie = specie.trim();

//------------------------------------------------------------------------------------------------------------

        String nickname = editTextNickname.getText().toString();

        if(nickname ==  null || nickname.trim().isEmpty()){
            Toast.makeText(this, R.string.invalid_nickname_try_again, Toast.LENGTH_LONG).show();

            editTextNickname.setText(null);
            editTextNickname.requestFocus();

            return;
        }

        nickname = nickname.trim();

//------------------------------------------------------------------------------------------------------------

        String levelString = editTextLevel.getText().toString();

        if(levelString ==  null || levelString.trim().isEmpty()){
            Toast.makeText(this, R.string.invalid_level_try_again, Toast.LENGTH_LONG).show();

            editTextLevel.setText(null);
            editTextLevel.requestFocus();

            return;
        }

        int level = 0;

        try {
            level = Integer.parseInt(levelString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.level_must_be_an_integer, Toast.LENGTH_LONG).show();

            editTextLevel.setText(null);
            editTextLevel.requestFocus();

            return;
        }

        if(level < 1 || level > 100) {
            Toast.makeText(this, R.string.pokemon_level_between_1_and_100, Toast.LENGTH_LONG).show();

            editTextLevel.setText(null);
            editTextLevel.requestFocus();

            return;
        }

//------------------------------------------------------------------------------------------------------------

        String primaryType = (String) spinnerPrimaryType.getSelectedItem();

        if(primaryType == null){
            Toast.makeText(this, R.string.primary_type_spinner_no_value, Toast.LENGTH_LONG).show();
            return;
        }

        String secondaryType = (String) spinnerSecondaryType.getSelectedItem();

        if(secondaryType == null){
            Toast.makeText(this, R.string.secondary_type_spinner_no_value, Toast.LENGTH_LONG).show();
            return;
        }

//------------------------------------------------------------------------------------------------------------

        int radioButtonId = radioGroupOrigin.getCheckedRadioButtonId();
        PokemonOrigin pokemonOrigin;

        if(radioButtonId == R.id.radioButtonEgg){
            pokemonOrigin = PokemonOrigin.Egg;
        } else if (radioButtonId == R.id.radioButtonCapture) {
            pokemonOrigin = PokemonOrigin.Capture;
        } else if (radioButtonId == R.id.radioButtonTrade) {
            pokemonOrigin = PokemonOrigin.Trade;
        } else if (radioButtonId == R.id.radioButtonFossil) {
            pokemonOrigin = PokemonOrigin.Fossil;
        } else {
            Toast.makeText(this, R.string.pokemon_origin_is_required, Toast.LENGTH_LONG).show();
            return;
        }

//------------------------------------------------------------------------------------------------------------

        boolean addToParty = checkBoxAddParty.isChecked();

//------------------------------------------------------------------------------------------------------------

        String types = secondaryType.equals(getString(R.string.none)) ? primaryType : primaryType + getString(R.string.type_separator) + secondaryType;

        Intent intentResponse = new Intent();

        intentResponse.putExtra(KEY_SPECIE, specie);
        intentResponse.putExtra(KEY_NICKNAME, nickname);
        intentResponse.putExtra(KEY_LEVEL, level);
        intentResponse.putExtra(KEY_TYPES, types);
        intentResponse.putExtra(KEY_ORIGIN, pokemonOrigin.toString());
        intentResponse.putExtra(KEY_PARTY, addToParty);

        setResult(PokemonActivity.RESULT_OK, intentResponse);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokemon_options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSave){
            saveValues();
            return true;
        } else if (idMenuItem == R.id.menuItemClear) {
            clearFields();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}