package com.distributed.distributed2017androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.distributed.distributed2017androidapp.Controller.HandleConnections;
import com.distributed.distributed2017androidapp.Model.Directions;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private String startLon,startLat,endLat,endLon;
    EditText startlat, startlon, endlat, endlon;
    Directions askedDirs=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startlat = (EditText) findViewById(R.id.startlat);
        startlon = (EditText) findViewById(R.id.startlon);
        endlat = (EditText) findViewById(R.id.endlat);
        endlon = (EditText) findViewById(R.id.endlon);
        final Button getDirs = (Button) findViewById(R.id.GetDirs);
        getDirs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(canUse()){
                    askedDirs= new Directions(Double.parseDouble(startLat),Double.parseDouble(startLon),Double.parseDouble(endLat),Double.parseDouble(endLon));
                    Log.i("OurDirs",askedDirs.toString());
                    HandleConnections handleConnections = new HandleConnections("192.168.1.87",4321,askedDirs);
                    handleConnections.execute();
                    Toast.makeText(getApplicationContext(),handleConnections.getOurDirs().getDirs(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"We cannot use the inputs ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean canUse() {
        startLat = startlat.getText().toString().trim();
        startLon = startlon.getText().toString().trim();
        endLat = endlat.getText().toString().trim();
        endLon = endlon.getText().toString().trim();
        //Toast.makeText(getApplicationContext(),startLat+" "+startLon,Toast.LENGTH_LONG).show();
        return !(startLat.equals("") || startLon.equals("") || endLat.equals("") || endLon.equals(""))
                && isDouble(startLat) && isDouble(startLon) && isDouble(endLat) && isDouble(endLon);

    }
    private boolean isDouble(String str) {
        try {
            BigDecimal myDec = new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            Log.e(e.getMessage(),e.getMessage());
            return false;
        }
    }
}
