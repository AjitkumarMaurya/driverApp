package com.docuser.driveroncall.Activity;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.docuser.driveroncall.R;
import com.docuser.driveroncall.common.Common;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogDateTimeFragment extends DialogFragment {

    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    TimePickerDialog picker;
    HorizontalCalendar horizontalCalendar;

    public DialogDateTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.alert_label_editor, container, false);

        Calendar tDate = Calendar.getInstance();
        tDate.add(Calendar.MONTH, 0);
        tDate.add(Calendar.DAY_OF_MONTH,-1);

        Log.e("@@",""+tDate);
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);
        startDate.add(Calendar.DAY_OF_MONTH,-1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        horizontalCalendar = new HorizontalCalendar.Builder(v, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .defaultSelectedDate(tDate)
                .build();

        return v;
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View dialogView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(dialogView, savedInstanceState);

        EditText date = dialogView.findViewById(R.id.date);
        EditText time = dialogView.findViewById(R.id.time);
        Button cancel = dialogView.findViewById(R.id.cancel);
        Button ok = dialogView.findViewById(R.id.ok);
        date.setText("Select date by scrolling dates");


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(java.util.Calendar c, int position) {

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                Common.date = mYear + "-" + checkDigit(mMonth+1) + "-" +checkDigit(mDay+1);

                Log.e("@@",""+mYear + "-" + checkDigit(mMonth) + "-" +checkDigit(mDay+1));

                date.setText(""+mYear + "-" + checkDigit(mMonth+1) + "-" +checkDigit(mDay+1));
                date.setTag("1");
            }

        });




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ok.setOnClickListener(v1 ->

                {
                    if (date.getTag().toString().equalsIgnoreCase("1")) {
                        if (!time.getText().toString().trim().isEmpty()) {

                            if (Common.oneOrTwoWay.equalsIgnoreCase("2")) {
                                final Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.popupxml);


                                TextView txt_inHours = dialog.findViewById(R.id.txt_inHours);
                                TextView txt_inDays = dialog.findViewById(R.id.txt_inDays);

                                txt_inDays.setOnClickListener(v2 -> {

                                    txt_inDays.setBackgroundResource(R.drawable.boder_blue);
                                    txt_inHours.setBackgroundResource(R.drawable.boder_white);

                                    Common.indayorhour = "1";
                                });
                                txt_inHours.setOnClickListener(v2 -> {

                                    txt_inHours.setBackgroundResource(R.drawable.boder_blue);
                                    txt_inDays.setBackgroundResource(R.drawable.boder_white);
                                    Common.indayorhour = "2";

                                });

                                Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                                btn_cancel.setOnClickListener(v14 -> dialog.dismiss());
                                Button btn_ok = dialog.findViewById(R.id.btn_ok);
                                btn_ok.setOnClickListener(v15 -> {
                                    dialog.dismiss();
                                    startActivity(new Intent(getActivity(), EstimateCostActivity.class));

                                });

                                dialog.show();
                            } else {
                                dismiss();
                                startActivity(new Intent(getActivity(), EstimateCostActivity.class));

                            }

                        }else {

                            Toast.makeText(getActivity(), "Select correct time.", Toast.LENGTH_SHORT).show();

                        }


                    }else {

                        Toast.makeText(getActivity(), "Select correct date.", Toast.LENGTH_SHORT).show();

                    }



                }
        );



        // date picker dialog


        // Launch Time Picker Dialog



        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final Calendar c = Calendar.getInstance();

                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
                    time.setShowSoftInputOnFocus(false);
                } else { // API 11-20
                    time.setTextIsSelectable(true);
                }


                new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {


                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                Log.e("@@","h---"+hourOfDay+"----"+minute);

                                if (hourOfDay<=6) {

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                    builder1.setMessage("Sorry! Our driver are not available at that time, please select a time between 06:00 AM and 11:59 PM");
                                    builder1.setCancelable(true);

                                    AlertDialog alert11 = builder1.create();
                                    alert11.getWindow().setGravity(Gravity.BOTTOM);
                                    alert11.show();

                                }else {
                                    time.setText(String.format("%02d:%02d", hourOfDay, minute));

                                    Common.time = String.format("%02d:%02d", hourOfDay, minute);

                                    Log.e("@@", "" + String.format("%02d:%02d", hourOfDay, minute));
                                }



                            }
                        }, mHour, mMinute, false).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {


                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                Log.e("@@","h---"+hourOfDay+"----"+minute);

                                if (hourOfDay<=6) {

                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                    builder1.setMessage("Sorry! Our driver are not available at that time, please select a time between 06:00 AM and 11:59 PM");
                                    builder1.setCancelable(true);

                                    AlertDialog alert11 = builder1.create();
                                    alert11.getWindow().setGravity(Gravity.BOTTOM);
                                    alert11.show();

                                }else {
                                    time.setText(String.format("%02d:%02d", hourOfDay, minute));

                                    Common.time = String.format("%02d:%02d", hourOfDay, minute);

                                    Log.e("@@", "" + String.format("%02d:%02d", hourOfDay, minute));
                                }


                            }
                        }, mHour, mMinute, false).show();


            }
        });



    }
    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
}
