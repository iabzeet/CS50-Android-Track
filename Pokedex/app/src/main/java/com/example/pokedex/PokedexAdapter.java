package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                    intent.putExtra("url", current.getUrl());

                    v.getContext().startActivity(intent);
                }
            });
            }
        }


    private List<Pokemon> pokemon = new ArrayList<>();
    private RequestQueue requestQueue;

    PokedexAdapter(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        loadPokenon();
    }

    //method to load data from api
    public void loadPokenon() {
        String url = "https://pokeapi.co/api/v2/pokemon?limit=151";
        //wrtie out a json request using volley that will handle json response
        //last param--method that will be called when the request finishes
        //defining an object that represents a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override   //this method will be called when the data finishes loading
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i=0; i<results.length();i++) {
                        JSONObject result = results.getJSONObject(i);
                        String name = result.getString("name");
                        pokemon.add(new Pokemon(
                                name.substring(0, 1).toUpperCase() + name.substring(1),
                                result.getString("url")
                        ));
                    }
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("cs50", "Json error", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("cs50", "Pokemon list error", volleyError);
            }
        });
        //to use the request add to this queue
        //this queue will actually make that request with the url
        requestQueue.add(request);
    }

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
