package com.distributed.distributed2017androidapp.Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.distributed.distributed2017androidapp.Model.Directions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Created by tasos on 5/8/2017.
 */

public class HandleConnections extends AsyncTask<Object, Object, String> {
    String dstAddress;
    int dstPort;
    String response = "";
    Directions askedDirs, ourDirs;
    public HandleConnections(String address, int port, Directions askedDirs) {
        dstAddress = address;
        dstPort = port;
        this.askedDirs = askedDirs;
    }

    public Directions getOurDirs() {
        return ourDirs;
    }

    @Override
    protected String doInBackground(Object... arg0) {

        Socket socket = null;
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            socket = new Socket(dstAddress, dstPort);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            if(askedDirs==null)
                Log.e("isError","iserrorrrrr");
            objectOutputStream.writeObject(this.askedDirs);
            objectOutputStream.flush();
            this.ourDirs=(Directions)objectInputStream.readObject();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.getMessage();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i("Our Dirs:",this.getOurDirs().getDirs());

    }

}