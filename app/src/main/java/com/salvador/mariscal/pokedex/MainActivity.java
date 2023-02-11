package com.salvador.mariscal.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.salvador.mariscal.pokedex.Model.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Request mQueue;
    ImageView imagen;
    TextView nombre;
    ImageView btnSiguiente, btnAnterior;
    private ArrayList<Pokemon> lista = new ArrayList<Pokemon>();
    String url = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20";
    int indice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestDatos();
        imagen = findViewById(R.id.fotoImageView);
        nombre = findViewById(R.id.nombre);
        btnSiguiente = findViewById(R.id.btnSig);
        btnAnterior = findViewById(R.id.btnAnt);
        btnSiguiente.setOnClickListener(this);
        btnAnterior.setOnClickListener(this);
    }

    public void requestDatos(){
        RequestQueue cola = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en la conexion", Toast.LENGTH_LONG).show();
            }
        });
        cola.add(jsonObjectRequest);
    }

    public void parse(JSONObject response){
        try {
            String cadena = "";
            JSONArray pokemons = response.getJSONArray("results");
            for (int i = 0 ; i < pokemons.length(); i++) {
                JSONObject pok = pokemons.getJSONObject(i);
                String nombre = pok.getString("name");
                //Toast.makeText(getApplicationContext(), nombre, Toast.LENGTH_LONG).show();
                String url = pok.getString("url");
                //String[] urlPartes = url.split("/");
                //cadena = cadena + Integer.parseInt(urlPartes[urlPartes.length - 1]) + "," + nombre + "\n";
                //Toast.makeText(getApplicationContext(), ""+(urlPartes[urlPartes.length - 1]), Toast.LENGTH_LONG).show();
                Pokemon pkmn = new Pokemon(nombre,url);
                //Toast.makeText(getApplicationContext(), pkmn.getId()+"", Toast.LENGTH_LONG).show();
                this.lista.add(pkmn);
                //Toast.makeText(getApplicationContext(), lista.get(0).getId()+"", Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(getApplicationContext(),"Id = "+ cs.get(1).getId(), Toast.LENGTH_LONG).show();
            // dato.setText(cadena);
            mostrar(0);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void mostrar(int i){
        nombre.setText(lista.get(i).getName());
        Glide.with(getApplicationContext())
                .load(lista.get(i).getUrlImagen())
                .centerCrop()
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagen);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAnt:
                if (indice == 0) {
                    indice = lista.size()-1;
                    mostrar(indice);
                } else {
                    indice--;
                    mostrar(indice);
                }
                //Toast.makeText(getApplicationContext(),indice+"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSig:
                if (indice == lista.size()-1) {
                    indice = 0;
                    mostrar(indice);
                } else {
                    indice++;
                    mostrar(indice);
                }
                //Toast.makeText(getApplicationContext(),indice+"", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}