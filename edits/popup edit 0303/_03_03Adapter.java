package com.example.apit.task.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.CheckBox;
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
import com.example.apit.task.model.FailureFixing03_03;
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

    LayoutInflater inflater;
    View layout;
    Context context = App.getContext();

    TextView timeEditText;
    EditText dayEditText, dayNoEditText, yearEditText, other;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner monthSpinner;


    public _03_03Adapter(FailureFixing03_03[] _03_03Files) {
        this._03_03Files = _03_03Files;
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

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.edit_03_03_popup_window, null);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the devices screen density to calculate correct pixel sizes
                float density = context.getResources().getDisplayMetrics().density;
                // create a focusable PopupWindow with the given layout and correct size
                final PopupWindow pw = new PopupWindow(layout, (int) density * 240, ViewPager.LayoutParams.WRAP_CONTENT, true);
                pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pw.setTouchInterceptor(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            pw.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                pw.setOutsideTouchable(true);


                final String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                        "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
                monthSpinner = layout.findViewById(R.id.monthSpinner);
                monthSpinner.setAdapter(spinnerArrayAdapter);

                timeEditText = layout.findViewById(R.id.timeEditText);
                timeEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            final Calendar c = Calendar.getInstance();
                            int mHour = c.get(Calendar.HOUR_OF_DAY);
                            int mMinute = c.get(Calendar.MINUTE);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(App.getContext(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                    timeEditText.setText(hourOfDay + ":" + minute);

                                }
                            }, mHour, mMinute, false);

                            timePickerDialog.show();
                    }
                });

                dayEditText = layout.findViewById(R.id.day);
                dayNoEditText = layout.findViewById(R.id.dayNoEditText);
                yearEditText = layout.findViewById(R.id.yearEditText);
                other = layout.findViewById(R.id.other);
                radioGroup = layout.findViewById(R.id.radioGroup);

                FloatingActionButton close = layout.findViewById(R.id.floatingCloseButton);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dayEditText.getText() == null || dayNoEditText.getText() == null || yearEditText.getText() == null) {
                            Toast.makeText(context, "من فضلك ادخل جميع الحقول لحفظ التغيرات", Toast.LENGTH_LONG).show();
                        } else {
                            DateFormat format = new SimpleDateFormat("h:mm", Locale.ENGLISH);
                            try {
                                Date date = format.parse(timeEditText.getText().toString());
                                _03_03Files[i].Solving_Time = date;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            _03_03Files[i].Solvng_Day = dayEditText.getText().toString();
                            _03_03Files[i].Solving_Date = ArabicNumber(dayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition() + 1)) + "/" +
                                    ArabicNumber(yearEditText.getText().toString());
                            _03_03Files[i].LAST_SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(context, context.getString(R.string.user_id)));

                            // get selected radio button from radioGroup
                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            // find the radiobutton by returned id
                            radioButton = layout.findViewById(selectedId);

                            if (radioButton.getText().equals("تم معالجة القصور فى الخدمات المذكورة من نقبل القائم بالخدمات فى الزمن المحدد أعلاه")) {
                                _03_03Files[i].Solve_Problem = true;
                            } else {
                                if (radioButton.getText().equals("انتهت المدة المحددة ولا زال الوضع كما هو عليه وسيتم معالجة القصور عن طريق مجموعة الخدمة الميدانية/ اللجنة المركزية")) {
                                    _03_03Files[i].Not_Solving_AfterTimeLimit = true;
                                } else {
                                    if (radioButton.getText().equals("تم معالجة القصور من قبل مجموعة الخدمة الميدانية")) {
                                        _03_03Files[i].Solved_ByServiceGroup = true;
                                    }
                                }
                            }
                            if (other.getText() == null) {
                                _03_03Files[i].Remark = other.getText().toString();
                            }

                            pw.dismiss();
                        }
                    }
                });

                try {
                    pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
