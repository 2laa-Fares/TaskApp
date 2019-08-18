package com.example.apit.task.view;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.utils.PrefUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;

public class _03_03Adapter extends RecyclerView.Adapter<_03_03Adapter._03_03ViewHolder> {

    FailureFixing03_03[] _03_03Files;
    NEW_TASKS task;
    Category category;

    Context context = App.getContext();


    public _03_03Adapter(FailureFixing03_03[] _03_03Files, NEW_TASKS task, Category category) {
        this._03_03Files = _03_03Files;
        this.task = task;
        this.category = category;
    }


    @NonNull
    @Override
    public _03_03ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._03_03_item, viewGroup, false);
        return new _03_03ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull _03_03ViewHolder holder, final int i) {
        holder.no.setText(String.valueOf(i + 1));
        if (_03_03Files[i].Visti_Time_AR != null) {
            holder.visit_time.setText(_03_03Files[i].Visti_Time_AR);
        }
        if (_03_03Files[i].Report_Visit_Date != null) {
            holder.visit_date.setText(_03_03Files[i].Report_Visit_Date);
        }
        if (_03_03Files[i].License_ID != null) {
            holder.permit_no.setText(_03_03Files[i].License_ID);
        }
        if (_03_03Files[i].License_Date != null) {
            holder.permit_date.setText(_03_03Files[i].License_Date);
        }
        if (_03_03Files[i].License_Date != null) {
            holder.permit_date.setText(_03_03Files[i].License_Date);
        }
        if (_03_03Files[i].Time_limit != null) {
            holder.period.setText(_03_03Files[i].Time_limit);
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edit03_03Activity.class);
                Bundle b = new Bundle();
                b.putSerializable("Task", task);
                b.putSerializable("Category", category);
                b.putSerializable("03-03", _03_03Files[i]);
                intent.putExtras(b);
                App.getContext().startActivity(intent);
                ((TaskActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (_03_03Files != null)
            return _03_03Files.length;
        else return 0;
    }

    public class _03_03ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id._no)
        TextView no;
        @BindView(R.id._visit_time)
        TextView visit_time;
        @BindView(R.id._visit_date)
        TextView visit_date;
        @BindView(R.id._permit_no)
        TextView permit_no;
        @BindView(R.id._permit_date)
        TextView permit_date;
        @BindView(R.id._period)
        TextView period;
        @BindView(R.id._edit)
        Button edit;

        public _03_03ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
