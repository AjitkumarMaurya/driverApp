package com.docuser.driveroncall.Adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.docuser.driveroncall.R;
import com.docuser.driveroncall.response.MyBookResponce;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.myHolder> {


    private MyBookResponce myBookResponce;
    private BookCancelInterface bookCancelInterface;
    public MyBookingAdapter(Context context, MyBookResponce myBookResponce) {
        this.myBookResponce = myBookResponce;
    }

    public void setUPInterFace(BookCancelInterface upInterFace){
        this.bookCancelInterface=upInterFace;
    }

    public interface BookCancelInterface{
        void click(String data, int pos);
        void pay(String data, int pos, String amount, String tripId);

    }
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_my_book, viewGroup, false);

        return new myHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myHolder h, @SuppressLint("RecyclerView") int i) {

        h.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (h.lin_more.getVisibility()==View.VISIBLE){

                    h.lin_more.setVisibility(View.GONE);

                    h.btn_more.setText("MORE DETAILS");

                }else {

                    h.lin_more.setVisibility(View.VISIBLE);
                    h.btn_more.setText("HIDE DETAILS");

                }

            }
        });

        h.tv_trip_date.setText(myBookResponce.getMyBookings().get(i).getTripStartDate()+"");
        h.tv_trip_time.setText(myBookResponce.getMyBookings().get(i).getTripStartTime()+"");

        h.tv_trip_no.setText(myBookResponce.getMyBookings().get(i).getTripUniqueId()+"");
        h.tv_trip_pick_poin.setText(myBookResponce.getMyBookings().get(i).getTripAstPickupPointName()+"");

        h.tv_drop_point.setText(myBookResponce.getMyBookings().get(i).getTripAstDropPointName()+"");
        h.tv_amount.setText(myBookResponce.getMyBookings().get(i).getTripAmount()+"");

        h.tv_hire_hour.setText(myBookResponce.getMyBookings().get(i).getTripAstUsage()+"");

        h.tv_driver_name.setText(myBookResponce.getMyBookings().get(i).getTripDriverName()+"");
        h.tv_driver_number.setText(myBookResponce.getMyBookings().get(i).getTripDriverMobile()+"");


        if (myBookResponce.getMyBookings().get(i).getTripStatusId().equalsIgnoreCase("1") || myBookResponce.getMyBookings().get(i).getTripStatusId().equalsIgnoreCase("2"))
        {
            h.btn_cancel.setVisibility(View.VISIBLE);
        }else {
            h.btn_cancel.setVisibility(View.GONE);

        }

        if (myBookResponce.getMyBookings().get(i).getTripStatusId().equalsIgnoreCase("7"))
        {
            h.btn_pay.setVisibility(View.VISIBLE);
        }else {
            h.btn_pay.setVisibility(View.GONE);

        }

        if (myBookResponce.getMyBookings().get(i).getTripStatusId().equalsIgnoreCase("5"))
        {
            h.iv_bg.setVisibility(View.VISIBLE);
            h.iv_bg.setImageResource(R.drawable.completw_bg);

        }else if (myBookResponce.getMyBookings().get(i).getTripStatusId().equalsIgnoreCase("6"))
        {
            h.iv_bg.setVisibility(View.VISIBLE);
            h.iv_bg.setImageResource(R.drawable.cancel_bg);
        }else {
            h.iv_bg.setVisibility(View.GONE);

        }

        h.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookCancelInterface.click(String.valueOf(myBookResponce.getMyBookings().get(i).getTripId()),i);

            }
        });

        h.btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookCancelInterface.pay("",i,myBookResponce.getMyBookings().get(i).getTripAmount()+"",String.valueOf(myBookResponce.getMyBookings().get(i).getTripId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myBookResponce.getMyBookings().size();
    }

    class myHolder extends RecyclerView.ViewHolder{

        TextView tv_trip_no,tv_trip_pick_poin,tv_trip_date,tv_trip_time,tv_drop_point,tv_amount,tv_driver_name,tv_driver_number,tv_hire_hour;

        Button btn_more,btn_cancel,btn_pay;

        LinearLayout lin_more;
        ImageView iv_bg;

         myHolder(@NonNull View itemView) {
            super(itemView);

            tv_trip_no = itemView.findViewById(R.id.tv_trip_no);
             tv_trip_pick_poin = itemView.findViewById(R.id.tv_trip_pick_poin);
             tv_trip_date = itemView.findViewById(R.id.tv_trip_date);
             tv_trip_time = itemView.findViewById(R.id.tv_trip_time);
             btn_more = itemView.findViewById(R.id.btn_more);

             tv_drop_point = itemView.findViewById(R.id.tv_drop_point);
             tv_amount = itemView.findViewById(R.id.tv_amount);
             tv_driver_name = itemView.findViewById(R.id.tv_driver_name);
             tv_driver_number = itemView.findViewById(R.id.tv_driver_number);
             btn_cancel = itemView.findViewById(R.id.btn_cancel);
             tv_hire_hour = itemView.findViewById(R.id.tv_hire_hour);
             lin_more = itemView.findViewById(R.id.lin_more_details);
             btn_pay = itemView.findViewById(R.id.btn_pay);
             iv_bg = itemView.findViewById(R.id.iv_bg);

         }
    }
}
