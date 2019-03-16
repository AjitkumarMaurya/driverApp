package com.docuser.driveroncall.Adepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.docuser.driveroncall.R;

import java.util.ArrayList;

public class NumAddAdepter extends RecyclerView.Adapter<NumAddAdepter.NumModel> {
    Context context;
    NumClick numClick;
    int selectedPosition = -1;

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

        numModel.num.setText(list.get(i));

        if (selectedPosition == i){
            numModel.lin_root.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            numModel.num.setTextColor(context.getResources().getColor(R.color.white));

        }else {

            numModel.lin_root.setBackgroundColor(context.getResources().getColor(R.color.white));
            numModel.num.setTextColor(context.getResources().getColor(R.color.black));

        }


        numModel.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = i;
                numClick.click(list.get(i),i);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NumModel extends RecyclerView.ViewHolder {
        TextView num;
        LinearLayout lin_root;

        public NumModel(@NonNull View itemView) {

            super(itemView);

            lin_root =itemView.findViewById(R.id.lin_root);
            num = itemView.findViewById(R.id.tvNum);
        }
    }

    public interface NumClick{
        void click(String data, int pos);
    }

    public void SetupInterface(NumClick numClick){
        this.numClick=numClick;
    }
}
