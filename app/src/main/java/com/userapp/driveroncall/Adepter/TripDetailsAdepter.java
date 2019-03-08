package com.userapp.driveroncall.Adepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abc.driveroncall.R;
import com.userapp.driveroncall.model.TripDeteilsModel;

import java.util.ArrayList;

public class TripDetailsAdepter extends RecyclerView.Adapter<TripDetailsAdepter.TripDetailsModel> {


    Context context;
    ArrayList<TripDeteilsModel> tripDeteilsList;

    public TripDetailsAdepter(Context context, ArrayList<TripDeteilsModel> tripDeteilsList) {
        this.context = context;
        this.tripDeteilsList = tripDeteilsList;
    }



    @NonNull
    @Override
    public TripDetailsModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_trip_detalls, viewGroup, false);
        return new TripDetailsModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripDetailsModel tripDetailsModel, int i) {

        tripDetailsModel.tvGreenLocationName.setText(tripDeteilsList.get(i).getTripAstPickupPointName());
        tripDetailsModel.tvRedLocationNamen.setText(tripDeteilsList.get(i).getTripAstDropPointName());


        tripDetailsModel.tvDate.setText(tripDeteilsList.get(i).getTripEndDate());
        tripDetailsModel.tvTime.setText(tripDeteilsList.get(i).getTripEndTime());
        tripDetailsModel.tvReting.setText(tripDeteilsList.get(i).getTripEndTime());


    }

    @Override
    public int getItemCount() {
        return tripDeteilsList.size();
    }

    public class TripDetailsModel extends RecyclerView.ViewHolder {
        TextView tvGreenLocationName, tvRedLocationNamen, tvTripType, tvDate, tvKm, tvAmount, tvTime, tvReting;


        public TripDetailsModel(@NonNull View itemView) {
            super(itemView);


            tvGreenLocationName = itemView.findViewById(R.id.tvGreenText);
            tvRedLocationNamen = itemView.findViewById(R.id.tvradeText);
            tvTripType = itemView.findViewById(R.id.tvTripType);
            tvDate = itemView.findViewById(R.id.tvTrip_Date);
            tvKm = itemView.findViewById(R.id.tvDistonsh_Km);
            tvAmount = itemView.findViewById(R.id.tvPayment);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvReting = itemView.findViewById(R.id.retingBar);


        }
    }
}
