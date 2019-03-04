package com.abc.driveroncall.Adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abc.driveroncall.R;
import com.abc.driveroncall.response.MyBookResponce;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.myHolder> {


    Context context;
    MyBookResponce myBookResponce;

    public MyBookingAdapter(Context context, MyBookResponce myBookResponce) {
        this.context = context;
        this.myBookResponce = myBookResponce;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_my_book, viewGroup, false);

        return new myHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myHolder h, int i) {

        h.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        h.tv_trip_date.setText(myBookResponce.getMyBookings().get(i).getTripStartDate()+"");
        h.tv_trip_time.setText(myBookResponce.getMyBookings().get(i).getTripStartTime()+"");

        h.tv_trip_no.setText(myBookResponce.getMyBookings().get(i).getTripUniqueId()+"");
        h.tv_trip_pick_poin.setText(myBookResponce.getMyBookings().get(i).getTripAstPickupPointName()+"");

    }

    @Override
    public int getItemCount() {
        return myBookResponce.getMyBookings().size();
    }

    class myHolder extends RecyclerView.ViewHolder{

        TextView tv_trip_no,tv_trip_pick_poin,tv_trip_date,tv_trip_time;

        Button btn_more;

         myHolder(@NonNull View itemView) {
            super(itemView);

            tv_trip_no = itemView.findViewById(R.id.tv_trip_no);
            tv_trip_pick_poin = itemView.findViewById(R.id.tv_trip_pick_poin);
            tv_trip_date = itemView.findViewById(R.id.tv_trip_date);
            tv_trip_time = itemView.findViewById(R.id.tv_trip_time);
            btn_more = itemView.findViewById(R.id.btn_more);
        }
    }
}
