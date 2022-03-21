package com.example.stars.adapter;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.stars.R;
import com.example.stars.beans.Star;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarHolder> implements Filterable {

    private List<Star> stars;
    private LayoutInflater inflater;
    private Context cxt;
    private List<Star> starsFilter;
    private NewFilter mfilter;

    public StarAdapter(Context cxt, List<Star> stars) {
        this.stars = stars;
        this.cxt = cxt;
        this.inflater = LayoutInflater.from(cxt);
        starsFilter = new ArrayList<>();
        starsFilter.addAll(stars);
        mfilter = new NewFilter(this);
    }

    @NonNull
    @Override
    public StarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.star_item, parent, false);
        return new StarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarHolder holder, int position) {

        holder.nom.setText(starsFilter.get(position).getName());
        holder.stars.setRating(starsFilter.get(position).getStar());
        holder.id.setText(starsFilter.get(position).getId()+"");
        Glide.with(cxt)
                .load(starsFilter.get(position).getImg())
                .centerCrop()
                .apply(new RequestOptions().override(100, 100))
                .into(holder.photo);
        //holder.photo.setImageResource(stars.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return starsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    public class StarHolder extends RecyclerView.ViewHolder {
        TextView id, nom;
        RatingBar stars;
        ImageView photo;
        Dialog myDialog;
        public StarHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.ids);
            nom = itemView.findViewById(R.id.name);
            stars = itemView.findViewById(R.id.stars);
            photo = itemView.findViewById(R.id.img);
            myDialog = new Dialog(cxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(cxt, id.getText().toString(), Toast.LENGTH_SHORT).show();

                    TextView txtname, ids;
                    Button btnAnnul, btnOk;
                    RatingBar bar;
                    ImageView iv;
                    myDialog.setContentView(R.layout.activity_custom_popup);

                    ids =(TextView) myDialog.findViewById(R.id.idss);
                    btnAnnul = (Button) myDialog.findViewById(R.id.btnanull);
                    btnOk = (Button) myDialog.findViewById(R.id.okbtn);
                    bar = (RatingBar) myDialog.findViewById(R.id.ratingBar);
                    iv = myDialog.findViewById(R.id.img1);
                    ids.setText(id.getText());

                    bar.setRating(stars.getRating());
                    Glide.with(cxt)
                            .load(photo.getDrawable())
                            .centerCrop()
                            .apply(new RequestOptions().override(100, 100))
                            .into(iv);

                    btnAnnul.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            stars.setRating(bar.getRating());
                            myDialog.dismiss();
                        }
                    });
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();

                }
            });

        }
    }

    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;
        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            starsFilter.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                starsFilter.addAll(stars);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Star p : stars) {
                    if (p.getName().toLowerCase().startsWith(filterPattern)) {
                        starsFilter.add(p);
                    }
                }
            }
            results.values = starsFilter;
            results.count = starsFilter.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            starsFilter = (List<Star>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }



}
