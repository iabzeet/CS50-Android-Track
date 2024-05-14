package com.example.pokedex;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

//this reperesent all of the data inside recyclerview
//class Adapter is a genrric class that takes as a type a viewholder
//viewholder--will hold view and allow us to manipulate whats on the screen
public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {
    public static class PokedexViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView textView;
        //base view--all the android views are gonna subclass from this view
        PokedexViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.pokedex_row);
            textView = view.findViewById(R.id.pokedex_row_text_view);

            //event handler when the row is tapped\
            //right in line defining a class and instantiating it
            //new View.OnClickListener()--instance of a class
            containerView.setOnClickListener(new View.OnClickListener() {
                @Override                   //this class has only one method onClick and it was View that was clicked
                public void onClick (View v){
                    Pokemon current = (Pokemon) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), PokemonActivity.class);              //what class we want to instantiate
                    intent.putExtra("name", current.getName());
                    intent.putExtra("number", current.getNumber());

                    v.getContext().startActivity(intent);
                }
            });
            }
        }


    private List<Pokemon> pokemon = Arrays.asList(
            new Pokemon("Bulbasaur", 1),
            new Pokemon("Ivysaur", 2),
            new Pokemon("Venusaur", 3)
    );

    //this method is called when we create a new viewholder
    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //we want to go from layout to a view
        //inflate()--going from xml file to a java view
        //convert xml file to a java object in memory
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokedex_row, parent, false);

        return new PokedexViewHolder(view);
    }

    //going from model to this view--controller
    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        Pokemon current = pokemon.get(position);
        holder.textView.setText(current.getName());
        holder.containerView.setTag(current);              //setTag--some object associated with a view
    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }
}
