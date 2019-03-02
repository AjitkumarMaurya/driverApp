package com.abc.driveroncall.Adepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abc.driveroncall.R;

import java.util.ArrayList;

public class NumAddAdepter extends RecyclerView.Adapter<NumAddAdepter.NumModel> {
    Context context;

    public NumAddAdepter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<String> list;

    @NonNull
    @Override
    public NumModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_num_adepter, viewGroup, false);

        return new NumModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumModel numModel, int i) {

        for (int n = 0; n < 8; n++) {
            Log.i("num", String.valueOf(n));
        }
        Log.i("dipak", String.valueOf(i));

        numModel.num.setText(String.valueOf(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NumModel extends RecyclerView.ViewHolder {
        TextView num;

        public NumModel(@NonNull View itemView) {


            super(itemView);

            num = itemView.findViewById(R.id.tvNum);
        }
    }
}
