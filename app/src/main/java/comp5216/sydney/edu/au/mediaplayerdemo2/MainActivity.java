package comp5216.sydney.edu.au.mediaplayerdemo2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Music> arrayList;
    private CustomMusicAdapter adapter;
    private ListView songList;
    private File[] files;
    private String[] fileNames;
    public static String[] filePaths;   //static because need to pass filepaths over to adapter class - that's where mediaplayer implemented
    private String[] artists;
    private MediaMetadataRetriever retriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return;
            }
        }

        songList = (ListView) findViewById(R.id.songList);
        arrayList = new ArrayList<>();


        //choosing which folder to look into. here is Music/new
        File dirDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC + "/new");
        if (dirDownload.isDirectory()) {
            files = dirDownload.listFiles();        //populating file array

            //String arrays for holding our file info.
            // using filePaths is a bit redundant, but makes Uri.parse below and in adapter class bit easier
            // rather than typing facking everything out with filenames to get a string
            filePaths = new String[files.length];
            fileNames = new String[files.length];

            //retriever for getting our artist metadata
            artists = new String[files.length];
            retriever = new MediaMetadataRetriever();



            for(int i = 0; i < files.length; i++) {

                //song info
                filePaths[i] = files[i].getAbsolutePath();
                fileNames[i] = files[i].getName();

                //artist info
                retriever.setDataSource(this, Uri.parse(filePaths[i]));
                artists[i] = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

                //new "music" item into arraylist
                arrayList.add(new Music(fileNames[i], artists[i]));
            }


        }


        //mediaplayer work done in adapter
        adapter = new CustomMusicAdapter(this, R.layout.custom_music_item, arrayList);
        songList.setAdapter(adapter);

    }
}