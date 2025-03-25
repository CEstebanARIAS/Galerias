package jdc.esteban.galerias;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private int filaSelect;
    private LinearLayout amigos, familia, trabajo, comida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        amigos = findViewById(R.id.contenedorAmigos);
        familia = findViewById(R.id.contenedorFamilia);
        trabajo = findViewById(R.id.contenedorTrabajo);
        comida = findViewById(R.id.contenedorComida);

        findViewById(R.id.btnSeleccionarAmigos).setOnClickListener(v -> {
            filaSelect = R.id.contenedorAmigos;
            abrirGaleria();
        });

        findViewById(R.id.btnSeleccionarFamilia).setOnClickListener(v -> {
            filaSelect = R.id.contenedorFamilia;
            abrirGaleria();
        });

        findViewById(R.id.btnSeleccionarTrabajo).setOnClickListener(v -> {
            filaSelect = R.id.contenedorTrabajo;
            abrirGaleria();
        });

        findViewById(R.id.btnSeleccionarComida).setOnClickListener(v -> {
            filaSelect = R.id.contenedorComida;
            abrirGaleria();
        });

        findViewById(R.id.btnEjemplo).setOnClickListener(v -> {
            cargarEjemplo();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Selecciona Imagenes"), PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            LinearLayout contenedor = findViewById(filaSelect);

            if(data.getClipData() != null){
                //SI EL USUARIO ELIGE VARIAS IMAGENES
                eliminarEjemplos(contenedor);
                int count = data.getClipData().getItemCount();
                for(int i = 0; i<count; i++){
                    Uri imagenUri = data.getClipData().getItemAt(i).getUri();
                    agregarImagen(contenedor, imagenUri);
                }
            }else if (data.getData()!=null){
                //SI EL USUARIO ELIGE UNA SOLA IMAGEN
                eliminarEjemplos(contenedor);
                Uri imagenUri = data.getData();
                agregarImagen(contenedor, imagenUri);
            }


        }
    }

    protected  void cargarEjemplo(){
        LinearLayout[] contendores = {amigos, familia, trabajo, comida};

        for (LinearLayout contenedor : contendores){
            for(int i=0; i<3; i++){
                ImageView cuadrado = new ImageView(this);
                cuadrado.setTag("ejemplo");
                cuadrado.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
                cuadrado.setPadding(8,8,8,8);
                cuadrado.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                cuadrado.setPadding(10, 10, 10, 10); // Espacio interior
                cuadrado.setImageDrawable(new ColorDrawable(Color.BLUE)); // Fondo azul
                // Agregar la imagen al layout vertical o horizontal que tengas
                contenedor.addView(cuadrado);
            }
        }
    }

    protected void eliminarEjemplos(LinearLayout contenedor){
        for (int i = contenedor.getChildCount()-1; i>=0; i++){
            View child = contenedor.getChildAt(i);
            Object tag = child.getTag();
            if(tag!= null && tag.equals("ejemplo")){
                contenedor.removeViewAt(i);
            }
        }
    }

    protected void agregarImagen(LinearLayout contenedor, Uri imagenUri){
        // Crear el ImageView dinámico
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
        imageView.setPadding(8,8,8,8);
        imageView.setImageURI(imagenUri);
        // Agregar la imagen al layout vertical o horizontal que tengas
        contenedor.addView(imageView);
    }


}