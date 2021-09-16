package developer007.magdy.code95weather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import developer007.magdy.code95weather.R;
import developer007.magdy.code95weather.data.API;
import developer007.magdy.code95weather.modules.GeographicCoordinates.GeographicCoordinates;
import developer007.magdy.code95weather.modules.forecast.ForeCastModule;


public class ForeCastGeoAdapter extends RecyclerView.Adapter<ForeCastGeoAdapter.ForeCastViewHolder> {

    private GeographicCoordinates list = new GeographicCoordinates();
    private String strTimeDate, strTimeDesc, strTimeDegree, strImgTime;

    @NonNull
    @Override
    public ForeCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForeCastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForeCastViewHolder holder, int position) {

        //Strings
        strTimeDate = list.getList().get(position).getDt_txt();
        strTimeDesc = list.getList().get(position).getWeather().get(0).getDescription();
        strTimeDegree = list.getList().get(position).getMain().getTemp() + "°";
        strImgTime = handlingImage(list.getList().get(position).getWeather().get(0).getIcon());

        //holders

        holder.tvTimeDate.setText(strTimeDate);
        holder.tvTimeDesc.setText(strTimeDesc);
        holder.tvTimeDegree.setText(strTimeDegree);
        Picasso.get().load(strImgTime).into(holder.imgTime);


    }

    @Override
    public int getItemCount() {
        if (list.getList() != null) {
            return list.getList().size();
        } else
            return 0;

    }

    public void setList(GeographicCoordinates list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ForeCastViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeDate, tvTimeDesc, tvTimeDegree;
        ImageView imgTime;

        public ForeCastViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);
            tvTimeDesc = itemView.findViewById(R.id.tvTimeDesc);
            tvTimeDegree = itemView.findViewById(R.id.tvTimeDegree);
            imgTime = itemView.findViewById(R.id.imgTime);
        }
    }


    public String handlingImage(String imageCode) {
        String strImage = "";


        API api = new API();
        strImage = api.getImageURl() + imageCode + api.getImgExtUrl();

        return strImage;


    }
}