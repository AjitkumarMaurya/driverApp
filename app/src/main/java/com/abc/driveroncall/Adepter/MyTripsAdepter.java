package com.abc.driveroncall.Adepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abc.driveroncall.R;
import com.abc.driveroncall.model.UserMyTripModel;

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


        tripModel.upTrip.setText(list.get(i).getTripAstPickupPointCityName());
        tripModel.downTrip.setText(list.get(i).getTripAstDropPointCityName());
        tripModel.upTripLocation.setText(list.get(i).getTripPickupPointName());
        tripModel.downTripLoaction.setText(list.get(i).getTripDropPointName());
        tripModel.tvTripType.setText(list.get(i).getTripsType());
        tripModel.tvTimeText.setText(list.get(i).getTripUsage());
        tripModel.tvPayment.setText(list.get(i).getTripAmount());
        tripModel.tvDate.setText(list.get(i).getTripEndDate());
        tripModel.tvTime.setText(list.get(i).getTripEndTime());

        tripModel.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
