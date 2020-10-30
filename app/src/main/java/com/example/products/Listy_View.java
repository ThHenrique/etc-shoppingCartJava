package com.example.products;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Listy_View extends ListActivity {


  ArrayList<JSONArray> productsList;
  ListAdapter adapter;
  JSONArray products = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    productsList = new ArrayList<JSONArray>();

    Exemplo ex = new Exemplo();
    ex.execute((String) "consulta");

  }

  void loadJson() {
    BufferedReader respostaServidor = null;

    try {
      URL url = new URL("http://10.0.2.2/www/PHP-Mobile-JSON/consulta_produtos.php");
      URLConnection conexao = url.openConnection();

      conexao.setDoOutput(true);
      respostaServidor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));

      StringBuilder respostaRecebida = new StringBuilder();
      String linhaRecebidaResposta = null;
      while ((linhaRecebidaResposta = respostaServidor.readLine()) != null) {
        respostaRecebida.append(linhaRecebidaResposta);
      }

      JSONArray jsonarray = new JSONArray(respostaRecebida.toString());
      System.out.println(jsonarray);

    }
    catch(Exception ex){
      System.out.println(ex);
    }
    finally{
      try {
        respostaServidor.close();
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }
  }

  public class Exemplo extends AsyncTask<String, String[], JSONArray> {
    @Override
    protected JSONArray doInBackground(String... params) {
      loadJson();
      return null;
    }


    @SuppressLint("WrongConstant")
    protected void onPostExecute(JSONArray result) {
      Toast.makeText(getApplicationContext(), "Finalizando.... ", 1000).show();



      productsList.add(result);

      ListView lv = (ListView) findViewById(R.id.listView1);

      lv.setAdapter(new SimpleAdapter(this, productsList,  ));

      adapter.notifyDataSetChanged();
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onPreExecute() {
      Toast.makeText(getApplicationContext(), "Iniciando.... ", 1000).show();
    }

  }
}
