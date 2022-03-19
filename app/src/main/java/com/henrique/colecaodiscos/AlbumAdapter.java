package com.henrique.colecaodiscos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.henrique.colecaodiscos.domain.Album;

import java.util.List;

public class AlbumAdapter extends BaseAdapter {

    Context context;
    List<Album> albums;

    private static class AlbumHolder {
        public ImageView imageViewCapa;
        public TextView textViewAlbum;
        public TextView textViewAno;
    }

    public AlbumAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AlbumHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_albums, parent, false);

            holder = new AlbumHolder();

            holder.imageViewCapa = convertView.findViewById(R.id.imageViewCapa);
            holder.textViewAlbum = convertView.findViewById(R.id.descriptionAlbum);
            holder.textViewAno = convertView.findViewById(R.id.yearAlbum);

            convertView.setTag(holder);
        } else {
            holder = (AlbumHolder) convertView.getTag();
        }

        holder.imageViewCapa.setImageDrawable(getImage(albums.get(position).getVinil()));
        holder.textViewAlbum.setText(albums.get(position).getNomeComGenero());
        holder.textViewAno.setText(albums.get(position).getAnoComItem());

        return convertView;

    }

    public void remove(Album album) {
        albums.remove(album);
        notifyDataSetChanged();
    }

    private Drawable getImage(boolean isVinil) {
        return isVinil ?
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.vinil, null) :
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.cd, null);
    }
}
