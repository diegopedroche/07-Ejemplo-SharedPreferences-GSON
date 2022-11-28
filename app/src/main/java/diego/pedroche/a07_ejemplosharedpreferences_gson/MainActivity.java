package diego.pedroche.a07_ejemplosharedpreferences_gson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ContactoMatricula> contactos;
    private TextView lbCantidad;
    private Button btnGuardar, btnCargar;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactos = new ArrayList<>();
        sharedPreferences = getSharedPreferences(Constantes.DATOS, MODE_PRIVATE);
        btnCargar = findViewById(R.id.btnCargarMain);
        btnGuardar = findViewById(R.id.btnGuardarMain);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactosSTR = new Gson().toJson(contactos);
                Log.d("JSON",contactosSTR);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constantes.CONTACTOS, contactosSTR);
                editor.apply();
            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.contains(Constantes.CONTACTOS) && !sharedPreferences.getString(Constantes.CONTACTOS,"").isEmpty()){
                    String contactosSTR = sharedPreferences.getString(Constantes.CONTACTOS,"");
                    Type tipo = new TypeToken< ArrayList<ContactoMatricula> >(){}.getType();
                    List<ContactoMatricula> temp = new Gson().fromJson(contactosSTR, tipo);
                    contactos.clear();
                    contactos.addAll(temp);
                    Toast.makeText(MainActivity.this, "Elementos cargados", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        if (!sharedPreferences.contains(Constantes.CONTACTOS)) {
            crearContactos();
        }


        lbCantidad = findViewById(R.id.lbCantidad);
        lbCantidad.setText("Tenemos: "+contactos.size()+" contactos");
    }

    private void crearContactos() {
        for (int i = 0; i < 10; i++) {
            contactos.add(new ContactoMatricula("Nombre "+i, "Ciclo "+i,"Email "+i,"Telefono "+i));
        }
    }
}