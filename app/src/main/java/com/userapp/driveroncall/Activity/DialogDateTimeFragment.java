package com.userapp.driveroncall.Activity;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abc.driveroncall.R;
import com.userapp.driveroncall.common.Common;

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

        tDate.add(Calendar.DAY_OF_MONTH,1);

        Log.e("@@",""+tDate);
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 1);
        startDate.add(Calendar.DAY_OF_MONTH,-1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);
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
        EditText time = (EditText) dialogView.findViewById(R.id.time);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button ok = (Button) dialogView.findViewById(R.id.ok);
        date.setText("Select date by scrolling dates");


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(java.util.Calendar c, int position) {

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                Common.date = mYear + "-" + checkDigit(mMonth) + "-" +checkDigit(mDay+1);

                Log.e("@@",""+mYear + "-" + checkDigit(mMonth) + "-" +checkDigit(mDay+1));

                date.setText(""+mYear + "-" + checkDigit(mMonth) + "-" +checkDigit(mDay+1));
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

                        if (Common.oneOrTwoWay.equalsIgnoreCase("2")) {
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.popupxml);


                            TextView txt_inHours = dialog.findViewById(R.id.txt_inHours);
                            TextView txt_inDays = dialog.findViewById(R.id.txt_inDays);

                            txt_inDays.setOnClickListener(v2 -> {

                                txt_inDays.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                txt_inHours.setBackgroundColor(getResources().getColor(R.color.white));

                                Common.indayorhour = "1";
                            });
                            txt_inHours.setOnClickListener(v2 -> {

                                txt_inHours.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                txt_inDays.setBackgroundColor(getResources().getColor(R.color.white));

                                Common.indayorhour = "2";

                            });

                            Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                            btn_cancel.setOnClickListener(v14 -> dialog.dismiss());
                            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
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

                new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {


                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                Toast.makeText(getActivity(), "" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));

                                Common.time = String.format("%02d:%02d", hourOfDay, minute);

                                Log.e("@@",""+String.format("%02d:%02d", hourOfDay, minute));


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

                                Toast.makeText(getActivity(), "" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                                time.setText(String.format("%02d:%02d", hourOfDay, minute));

                                Common.time = String.format("%02d:%02d", hourOfDay, minute);

                                Log.e("@@",""+String.format("%02d:%02d", hourOfDay, minute));


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
