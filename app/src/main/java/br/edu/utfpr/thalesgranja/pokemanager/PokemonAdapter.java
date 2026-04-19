package br.edu.utfpr.thalesgranja.pokemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PokemonAdapter extends BaseAdapter {

    private Context context;

    private List<Pokemon> listPokemons;

    private String[] types;

    private static class PokemonHolder {
        public TextView textViewValueSpecie;
        public TextView textViewValueNickname;
        public TextView textViewValueLevel;
        public TextView textViewValueType;
        public TextView textViewValueOrigin;
        public TextView textViewValueParty;
    }

    public PokemonAdapter(Context context, List<Pokemon> listPokemons) {
        this.context = context;
        this.listPokemons = listPokemons;

        types = context.getResources().getStringArray(R.array.pokemon_type);
    }

    @Override
    public int getCount() {
        return listPokemons.size();
    }

    @Override
    public Object getItem(int position) {
        return listPokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PokemonHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_list_pokemons, parent, false);

            holder = new PokemonHolder();

            holder.textViewValueSpecie = convertView.findViewById(R.id.textViewValueSpecie);
            holder.textViewValueNickname = convertView.findViewById(R.id.textViewValueNickname);
            holder.textViewValueLevel = convertView.findViewById(R.id.textViewValueLevel);
            holder.textViewValueType = convertView.findViewById(R.id.textViewValueType);
            holder.textViewValueOrigin = convertView.findViewById(R.id.textViewValueOrigin);
            holder.textViewValueParty = convertView.findViewById(R.id.textViewValueParty);

            convertView.setTag(holder);
        } else {
            holder = (PokemonHolder) convertView.getTag();
        }

        Pokemon pokemon = listPokemons.get(position);

        holder.textViewValueSpecie.setText(pokemon.getSpecie());
        holder.textViewValueNickname.setText(pokemon.getNickname());
        holder.textViewValueLevel.setText(String.valueOf(pokemon.getLevel()));
        holder.textViewValueType.setText(pokemon.getType());

        switch (pokemon.getPokemonOrigin()){
            case Egg:
                holder.textViewValueOrigin.setText(R.string.egg);
                break;

            case Capture:
                holder.textViewValueOrigin.setText(R.string.capture);
                break;

            case Trade:
                holder.textViewValueOrigin.setText(R.string.trade);
                break;

            case Fossil:
                holder.textViewValueOrigin.setText(R.string.fossil);
                break;
        }

        if (pokemon.isAddParty()){
            holder.textViewValueParty.setText(R.string.in_party);
        } else {
            holder.textViewValueParty.setText(R.string.not_in_party);
        }

        return convertView;
    }
}
