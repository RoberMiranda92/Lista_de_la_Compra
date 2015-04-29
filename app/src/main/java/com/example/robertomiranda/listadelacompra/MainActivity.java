package com.example.robertomiranda.listadelacompra;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> productos;
    private String cantidad = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Lista de productos
        productos = new ArrayList<String>();
        final ArrayAdapter<String> adaptadorProductos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productos);
        ListView listViewProductos = (ListView) findViewById(R.id.listProductos);
        listViewProductos.setAdapter(adaptadorProductos);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //Creamos el adaptador
        ArrayAdapter<CharSequence> adaptadorSpinner = ArrayAdapter.createFromResource(this, R.array.Numbers, android.R.layout.simple_spinner_item);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //asignamos el adaptador al Spinner
        spinner.setAdapter(adaptadorSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cantidad = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Listener del Boton
        Button botonAñadir = (Button) findViewById(R.id.botonAñadir);

        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Coger los datos del editText
                EditText nombreProducto = (EditText) findViewById(R.id.editText);
                productos.add(cantidad + " " + nombreProducto.getText().toString());
                adaptadorProductos.notifyDataSetChanged();
                //Mostrar Toast
                Snackbar.with(getApplicationContext()) // context
                        .text("Guardado").duration(Snackbar.SnackbarDuration.LENGTH_SHORT) // text to display
                        .show(MainActivity.this);

                //vaciar el editText
                nombreProducto.setText("");

            }
        });
        //Listener de eliminación
        listViewProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String eliminado = productos.remove(position);
                adaptadorProductos.notifyDataSetChanged();

                //Barra de eliminar
                SnackbarManager.show(Snackbar.with(getApplicationContext()).text("Eliminado").actionLabel("Deshacer").actionColor(Color.rgb(255, 200, 0)).actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        productos.add(position, eliminado);
                        adaptadorProductos.notifyDataSetChanged();
                    }
                }),MainActivity.this);
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
