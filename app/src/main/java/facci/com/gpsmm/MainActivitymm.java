package facci.com.gpsmm;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.List;

public class MainActivitymm extends AppCompatActivity {

    //#1 Agregar un LocationManager
    LocationManager locManager;
    //#1 Agregar los campos de latitud y longitud
    private Double latitud;
    private Double longitud;

    //#1 Agregar permisos en el Manifest
    //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    //<uses-permission android:name="android.permission.INTERNET" />
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitymm);
        //#2 Inicializar el Location Manager
        locManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //#3 Obtener los proveedores
        List<String> listaProviders = locManager.getAllProviders();

        //#4 Seleccionar el primer proveedor de la lista
        LocationProvider provider = locManager.getProvider(listaProviders.get(0));

        //#5 Revisar los datos de precisión, es capaz de obtener la altitud y el consumo
        int precision = provider.getAccuracy();
        boolean obtieneAltitud = provider.supportsAltitude();
        int consumoRecursos = provider.getPowerRequirement();
        Toast.makeText(MainActivitymm.this, String.format("Precision : %s \n Altitud : %s \n Consumo Recursos : %s",String.valueOf(precision),String.valueOf(obtieneAltitud),String.valueOf(consumoRecursos)), Toast.LENGTH_LONG).show();

    }

    //#3 Capturar el evento click para actualizar las coordenadas
    public void ActualizarCoordenadasClick(View v){

        //locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
        }

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);

    }


    //#2 Agregar un LocationListener
    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitud = location.getLongitude();
            latitud = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditText txtLongitud = (EditText) findViewById(R.id.txtLongitud);
                    EditText txtLatitud = (EditText) findViewById(R.id.txtLatitud);
                    txtLongitud.setText(longitud + "");
                    txtLatitud.setText(latitud + "");
                    Toast.makeText(MainActivitymm.this, "Coordenadas GPS Capturadas", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

}
