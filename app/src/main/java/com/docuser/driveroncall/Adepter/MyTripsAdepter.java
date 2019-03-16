package com.docuser.driveroncall.Adepter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.docuser.driveroncall.Activity.TripDetailsActivity;
import com.docuser.driveroncall.R;
import com.docuser.driveroncall.model.UserMyTripModel;

import java.util.ArrayList;

public class MyTripsAdepter extends RecyclerView.Adapter<MyTripsAdepter.TripModel> {

    Context context;
    ArrayList<UserMyTripModel> list;

    public MyTripsAdepter(Context context, ArrayList<UserMyTripModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TripModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_my_trip, viewGroup, false);


        return new TripModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripModel tripModel, int i) {

        String pickuppointlat, pickuppointlang, droppointlat, droppointlang, pickupCityName, dropCityName, PickupPointName, kms, dropPointName, tripType, tripUsage, tripAmount, tripEndDate, tripEndTime;

        pickupCityName = list.get(i).getTripAstPickupPointCityName();
        dropCityName = list.get(i).getTripAstDropPointCityName();
        PickupPointName = list.get(i).getTripAstPickupPointName();
        dropPointName = list.get(i).getTripAstDropPointName();
        tripType = list.get(i).getTripTypeId();
        tripUsage = list.get(i).getTripUsage();
        tripAmount = list.get(i).getTripAmount();
        tripEndDate = list.get(i).getTripEndDate();
        tripEndTime = list.get(i).getTripEndTime();
        pickuppointlat = list.get(i).getTripAstPickupPointLat();
        pickuppointlang = list.get(i).getTripAstPickupPointLang();
        droppointlat = list.get(i).getTripAstDropPointLat();
        droppointlang = list.get(i).getTripAstDropPointLang();
        kms = list.get(i).getTripKmsDriven();

        tripModel.upTrip.setText(list.get(i).getTripAstPickupPointCityName());
        tripModel.downTrip.setText(list.get(i).getTripAstDropPointCityName());
        tripModel.upTripLocation.setText(list.get(i).getTripAstPickupPointName());
        tripModel.downTripLoaction.setText(list.get(i).getTripAstDropPointName());
        tripModel.tvTripType.setText(list.get(i).getTripsType());
        tripModel.tvTimeText.setText(list.get(i).getTripUsage());
        tripModel.tvPayment.setText(list.get(i).getTripAmount());
        tripModel.tvDate.setText(list.get(i).getTripEndDate());
        tripModel.tvTime.setText(list.get(i).getTripEndTime());

        tripModel.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, TripDetailsActivity.class);
                myIntent.putExtra("pickupCityName", pickupCityName);
                myIntent.putExtra("dropCityName", dropCityName);
                myIntent.putExtra("PickupPointName", PickupPointName);
                myIntent.putExtra("dropPointName", dropPointName);
                myIntent.putExtra("tripType", tripType);
                myIntent.putExtra("tripUsage", tripUsage);
                myIntent.putExtra("tripAmount", tripAmount);
                myIntent.putExtra("tripEndDate", tripEndDate);
                myIntent.putExtra("tripEndTime", tripEndTime);
                myIntent.putExtra("kms", kms);
                myIntent.putExtra("pickuppointlat", pickuppointlat);
                myIntent.putExtra("pickuppointlang", pickuppointlang);
                myIntent.putExtra("droppointlat", droppointlat);
                myIntent.putExtra("droppointlang", droppointlang);
                context.startActivity(myIntent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TripModel extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView upTrip, downTrip, upTripLocation, downTripLoaction, tvTripType, tvTimeText, tvPayment, tvDate, tvTime;

        public TripModel(@NonNull View itemView) {
            super(itemView);
            upTrip = itemView.findViewById(R.id.tvPleshGoing);
            downTrip = itemView.findViewById(R.id.tvPleshFrom);
            upTripLocation = itemView.findViewById(R.id.tvGreenText);
            downTripLoaction = itemView.findViewById(R.id.tvradeText);
            tvTripType = itemView.findViewById(R.id.tvTripType);
            tvTimeText = itemView.findViewById(R.id.tvTimeText);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }
}
