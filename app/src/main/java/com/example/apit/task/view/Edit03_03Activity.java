package com.example.apit.task.view;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.JamaratRegiment01_09;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.QuestionnairesPresenter;
import com.example.apit.task.repositories.imp.QuestionnairesRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;

public class Edit03_03Activity extends AppCompatActivity implements QuestionnairesInterface{

    @BindView(R.id.day)
    EditText dayEditText;
    @BindView(R.id.dayNoEditText)
    EditText dayNoEditText;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearEditText)
    EditText yearEditText;
    @BindView(R.id.timeEditText)
    TextView timeEditText;
    @BindView(R.id.other)
    EditText other;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.floatingCloseButton)
    FloatingActionButton close;

    RadioButton radioButton;

    QuestionnairesInterface questionnairesInterface;
    QuestionnairesPresenter presenter;
    FailureFixing03_03 _03_03File;
    NEW_TASKS task;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_03_03);
        App.setContext(this);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        RelativeLayout spinner = (RelativeLayout) findViewById(R.id.spinnerlayout);
        spinner.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        task = (NEW_TASKS) extras.getSerializable("Task");
        category = (Category) extras.getSerializable("Category");
        _03_03File = (FailureFixing03_03) extras.getSerializable("03-03");

        final ImageButton logout = (ImageButton) findViewById(R.id.logout);
        logout.setBackgroundResource(R.mipmap.back);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Edit03_03Activity.this, TaskActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Task", task);
                b.putSerializable("Category", category);
                b.putInt("Tab", 1 );
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });

        TextView toolbarText = (TextView) findViewById(R.id.mytext);
        toolbarText.setText(" تعديل محضر القصور رقم: " + _03_03File.Report_ID + " الخاص بالمبنى رقم: " + task.HOUSING_BUILDING_ID);

        questionnairesInterface = this;

        presenter = new QuestionnairesPresenter(questionnairesInterface, new QuestionnairesRepositoryImp());
        final String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        monthSpinner.setAdapter(spinnerArrayAdapter);

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

        close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dayEditText.getText().toString().equals("") || dayNoEditText.getText().toString().equals("") || yearEditText.getText().toString().equals("")) {
                            Toast.makeText(Edit03_03Activity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();
                        } else {
                            DateFormat format = new SimpleDateFormat("h:mm", Locale.ENGLISH);
                            try {
                                Date date = format.parse(timeEditText.getText().toString());
                                _03_03File.Solving_Time = date;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            _03_03File.Solvng_Day = dayEditText.getText().toString();
                            _03_03File.Solving_Date = ArabicNumber(dayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition() + 1)) + "/" +
                                    ArabicNumber(yearEditText.getText().toString());
                            _03_03File.LAST_SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(Edit03_03Activity.this, getString(R.string.user_id)));

                            // get selected radio button from radioGroup
                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            // find the radiobutton by returned id
                            radioButton = (RadioButton) findViewById(selectedId);

                            if (radioButton.getText().equals("تم معالجة القصور فى الخدمات المذكورة من نقبل القائم بالخدمات فى الزمن المحدد أعلاه")) {
                                _03_03File.Solve_Problem = true;
                            } else {
                                if (radioButton.getText().equals("انتهت المدة المحددة ولا زال الوضع كما هو عليه وسيتم معالجة القصور عن طريق مجموعة الخدمة الميدانية/ اللجنة المركزية")) {
                                    _03_03File.Not_Solving_AfterTimeLimit = true;
                                } else {
                                    if (radioButton.getText().equals("تم معالجة القصور من قبل مجموعة الخدمة الميدانية")) {
                                        _03_03File.Solved_ByServiceGroup = true;
                                    }
                                }
                            }
                            if (!other.getText().toString().equals("")) {
                                _03_03File.Remark = other.getText().toString();
                            }
                    Gson gson = new Gson();
                    String json = gson.toJson(_03_03File);
                    presenter.questionnaires(json, "03-03", "Edit");
                }
            }
        });
    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم تعديل الاستبيان بنجاح", Toast.LENGTH_LONG).show();
        Intent i = new Intent(Edit03_03Activity.this, TaskActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Task", task);
        b.putSerializable("Category", category);
        b.putInt("Tab", 1 );
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    @Override
    public void NotSaved(String error) {
        Toast.makeText(this, "من فضلك افحص اتصالك بالانترنت وحاول بوقت آخرى", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Edit03_03Activity.this, TaskActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Task", task);
        b.putSerializable("Category", category);
        b.putInt("Tab", 1 );
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
