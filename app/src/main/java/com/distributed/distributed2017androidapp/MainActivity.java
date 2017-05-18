package com.distributed.distributed2017androidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.distributed.distributed2017androidapp.Controller.HandleConnections;
import model.Directions;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    static private String startLon,startLat,endLat,endLon;
    static EditText startlat, startlon, endlat, endlon;
    Directions askedDirs=null;
    HandleConnections handleConnections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startlat = (EditText) findViewById(R.id.startlat);
        startlon = (EditText) findViewById(R.id.startlon);
        endlat = (EditText) findViewById(R.id.endlat);
        endlon = (EditText) findViewById(R.id.endlon);
        final TextView textView = (TextView)findViewById(R.id.textView2);
        final Button getDirs = (Button) findViewById(R.id.GetDirs);
        final Switch select_route = (Switch)findViewById(R.id.select_route);
        getDirs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(canUse()){
                    Log.i("dvevf",startLat);
                    askedDirs= new Directions(Double.parseDouble(startLat),Double.parseDouble(startLon),Double.parseDouble(endLat),Double.parseDouble(endLon));
                    //Log.i("OurDirs",askedDirs.toString());
                    if(handleConnections==null) {
                        handleConnections = new HandleConnections("192.168.1.73", 4321, askedDirs);
                        handleConnections.setAskedDirs(askedDirs);
                        handleConnections.execute();
                    }else {
                        handleConnections.setAskedDirs(askedDirs);
                        handleConnections.execute();
                    }
                    while(handleConnections.getOurDirs()==null){
                        int i=0;
                        i++;
                    }
                    handleConnections.cancel(true);
                    Directions ourDirs = handleConnections.getOurDirs();
                    textView.setText(ourDirs.toString());
                    //Toast.makeText(getApplicationContext(),ourDirs.toString(),Toast.LENGTH_LONG).show();
                    /*Intent goToMaps = new Intent(MainActivity.this,MapsActivity.class);
                    goToMaps.putExtra("Directions",ourDirs);
                    startActivity(goToMaps);*/
                    handleConnections.setAskedDirs(null);
                    askedDirs=null;handleConnections=null;
                }else{
                    Toast.makeText(getApplicationContext(),"We cannot use the inputs ", Toast.LENGTH_LONG).show();
                }
            }
        });
        select_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchRoute();
            }
        });
    }

    private void switchRoute(){
        String Ostartlat=getResources().getString(R.string.OtherStartLat),
                Ostartlon = getResources().getString(R.string.OtherStartLon),
                Oendlat = getResources().getString(R.string.OtherEndLat),
                Oendlon = getResources().getString(R.string.OtherEndLon),
                Dstartlat = getResources().getString(R.string.DefaultStartLat),
                Dstartlon= getResources().getString(R.string.DefaultStartLon),
                Dendlat = getResources().getString(R.string.DefaultEndLat),
                Dendlon = getResources().getString(R.string.DefaultEndLon);
        if(startlat.getText().toString().equals(getResources().getString(R.string.DefaultStartLat))){
            startlat.setText(startLat=Ostartlat);
            startlon.setText(startLon=Ostartlon);
            endlat.setText(endLat=Oendlat);
            endlon.setText(endLon=Oendlon);
        }else{
            startlat.setText(startLat=Dstartlat);
            startlon.setText(startLon=Dstartlon);
            endlat.setText(endLat=Dendlat);
            endlon.setText(endLon=Dendlon);
        }
    }

    private static boolean canUse() {
        startLat = startlat.getText().toString().trim();
        startLon = startlon.getText().toString().trim();
        endLat = endlat.getText().toString().trim();
        endLon = endlon.getText().toString().trim();
        //Toast.makeText(getApplicationContext(),startLat+" "+startLon,Toast.LENGTH_LONG).show();
        return !(startLat.equals("") || startLon.equals("") || endLat.equals("") || endLon.equals(""))
                && isDouble(startLat) && isDouble(startLon) && isDouble(endLat) && isDouble(endLon);

    }
    private static boolean isDouble(String str) {
        try {
            BigDecimal myDec = new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            Log.e(e.getMessage(),e.getMessage());
            return false;
        }
    }
}
