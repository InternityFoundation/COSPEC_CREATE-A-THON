package tech.spirit.woshield;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Help_Adapter extends RecyclerView.Adapter<Help_Adapter.ViewHolder>{

    private ArrayList<Help_Location> list;

    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }


    Help_Adapter(Context context, ArrayList<Help_Location> items)
    {
        activity=(ItemClicked) context;
        list=items;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        TextView tvuserName,tvuserEmail,tvMessage,tvdateTime;
        ImageView ivLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvuserName=itemView.findViewById(R.id.tvUserName);
            tvuserEmail=itemView.findViewById(R.id.tvuserEmail);
            tvMessage=itemView.findViewById(R.id.tvMessage);
            tvdateTime=itemView.findViewById(R.id.tvdateTime);
                    ivLocation=itemView.findViewById(R.id.ivMap);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.onItemClicked(list.indexOf((Help_Location) v.getTag()));


                }
            });
        }
    }

    @NonNull
    @Override
    public Help_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.help_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Help_Adapter.ViewHolder viewHolder, int i) {

        if(list.get(i).getUserName()!=null) {
            viewHolder.itemView.setTag(list.get(i));
            Log.i(list.get(i).getUserName(),"data");
            viewHolder.tvuserName.setText(list.get(i).getUserName());
            viewHolder.tvuserEmail.setText(list.get(i).getUserEmail());
            viewHolder.tvMessage.setText(list.get(i).getMessage());
            viewHolder.tvdateTime.setText(list.get(i).getDateTime());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

}
