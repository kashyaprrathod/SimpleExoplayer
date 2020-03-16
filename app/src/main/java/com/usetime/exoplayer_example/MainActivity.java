package com.usetime.exoplayer_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> List_Of_videos = new ArrayList<>();
    RecyclerView rv;

    DisplayMetrics matrix = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindowManager().getDefaultDisplay().getMetrics(matrix);


        CheckPermisiion();



    }

    private void CheckPermisiion() {
        String[] Perm = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(this, Perm, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                GatList();

                rv = findViewById(R.id.rv);

                rv.setAdapter(new Rv_Adapter(List_Of_videos,matrix));
                rv.setLayoutManager(new GridLayoutManager(MainActivity.this,3));

            }
        });
    }

    private void GatList() {

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                List_Of_videos.add(cursor.getString(0));
            }
            cursor.close();
        }
        Log.e("all path", List_Of_videos.toString());
    }

}
class Rv_Adapter extends RecyclerView.Adapter<Rv_Adapter.Vholder>{
    ArrayList<String> list;
    Context c;
    DisplayMetrics matrix;
    Rv_Adapter(ArrayList<String> list, DisplayMetrics matrix){
        this.list = list;
        this.matrix = matrix;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlevideo,parent,false);
        return new Vholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, int position) {
        holder.SetData(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Vholder extends RecyclerView.ViewHolder{
        CardView cv;
        ImageView iv;

        public Vholder(@NonNull View itemView) {
            super(itemView);

            int w = matrix.widthPixels /2;
            cv = itemView.findViewById(R.id.cv);
            cv.setLayoutParams(new LinearLayout.LayoutParams(w,w));
            iv = itemView.findViewById(R.id.iv);
        }

        public void SetData(final int position) {
            Glide.with(c).asDrawable().load(list.get(position)).into(iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(c,Watchvideo.class);
                    i.putExtra("url",list.get(position));
                    c.startActivity(i);
                }
            });
        }
    }
}
