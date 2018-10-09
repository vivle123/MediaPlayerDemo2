package comp5216.sydney.edu.au.mediaplayerdemo2;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import static comp5216.sydney.edu.au.mediaplayerdemo2.MainActivity.filePaths;


public class CustomMusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Music> arrayList;
    private MediaPlayer mediaPlayer;
    private Boolean flag = true;

    public CustomMusicAdapter(Context context, int layout, ArrayList<Music> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtName, txtArtist;
        ImageView ivPlay, ivStop;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtArtist = (TextView) convertView.findViewById(R.id.txtArtist);
            viewHolder.ivPlay = (ImageView) convertView.findViewById(R.id.ivPlay);
            viewHolder.ivStop = (ImageView) convertView.findViewById(R.id.ivStop);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //populating each item in listview with music item and setting text
        final Music music = arrayList.get(position);
        viewHolder.txtName.setText(music.getName());
        viewHolder.txtArtist.setText(music.getArtist());

        //play button listener
        viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag) {

                    //using filePaths from main activity. same thing as below commented line if we wanted to use fileNames
                    //mediaPlayer = MediaPlayer.create(context, Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/new/"+ fileNames[position]));

                    mediaPlayer = MediaPlayer.create(context, Uri.parse(filePaths[position]));

                    flag = false;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    viewHolder.ivPlay.setImageResource((R.drawable.ic_play));
                } else {
                    mediaPlayer.start();
                    viewHolder.ivPlay.setImageResource((R.drawable.ic_pause));
                }

            }
        });

        //stop button listener
        viewHolder.ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    flag = true;
                }
                viewHolder.ivPlay.setImageResource((R.drawable.ic_play));

            }
        });
        return convertView;
    }
}
