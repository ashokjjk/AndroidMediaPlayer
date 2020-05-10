package com.ashok.Streamz;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView songsList;
    String[] listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songsList = (ListView) findViewById(R.id.songList);
        getPermission();
        Toast.makeText(getApplicationContext(),"Brought to you by Ashok Kumar",Toast.LENGTH_LONG).show();
    }

    /*
    * Obtain permission form user with Manifest.xml control and store it
    * */
    public void getPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        displayList();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    // Get the data from each folder and store it in temp memory
    public ArrayList<File> filterData(File file) {
        ArrayList<File> dataList = new ArrayList<>();
        File[] files = file.listFiles();

        for (File eachFile : files) {
            if (eachFile.isDirectory() && !eachFile.isHidden()) {
                dataList.addAll(filterData(eachFile));
            } else {
                if (eachFile.getName().endsWith(".mp3")) {
                    dataList.add(eachFile);
                }
            }
        }
        return dataList;
    }

    /*
    * Once permission is obtained get the directories data in internal storage of device
    * External storage(Sd card) data is not retrieved, since android native API doesn't allow that permission
    * All Mp3 files are filtered and extension is removed for listing in the list view
    * */
    void displayList() {
        final ArrayList<File> rawData = filterData(Environment.getExternalStorageDirectory());
        listItems = new String[rawData.size()];
        for (int i = 0; i < rawData.size(); i++) {
            listItems[i] = rawData.get(i).getName().replace(".mp3", "");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        songsList.setAdapter(adapter);

        /*
        * Once the listed song is selected the position of the song in the arraylist is retrived for future process
        * The name of the song and the song data is passed to next activity(PlayerActivity)
        * */
        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String songName = songsList.getItemAtPosition(position).toString();
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class)
                        .putExtra("songs", rawData).putExtra("songname", songName)
                        .putExtra("pos", position));
            }
        });
    }


}
