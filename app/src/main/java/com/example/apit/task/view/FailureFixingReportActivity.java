package com.example.apit.task.view;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;
import com.example.apit.task.model.FailureFixing03_03;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.FormsPresenter;
import com.example.apit.task.presenter.QuestionnairesPresenter;
import com.example.apit.task.repositories.imp.QuestionnairesRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;

public class FailureFixingReportActivity extends AppCompatActivity implements QuestionnairesInterface {

    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.reportNoEditText)
    EditText reportNoEditText;
    @BindView(R.id.dayNoEditText)
    EditText dayNoEditText;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearEditText)
    EditText yearEditText;
    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.buildingNoEditText)
    Spinner buildingNoEditText;
    @BindView(R.id.neighborhoodEditText)
    EditText neighborhoodEditText;
    @BindView(R.id.streetEditText)
    EditText streetEditText;
    @BindView(R.id.besideEditText)
    EditText besideEditText;
    @BindView(R.id.permitNoEditText)
    EditText permitNoEditText;
    @BindView(R.id.permitDayNoEditText)
    EditText permitDayNoEditText;
    @BindView(R.id.permitMonthSpinner)
    Spinner permitMonthSpinner;
    @BindView(R.id.permitYearEditText)
    EditText permitYearEditText;
    @BindView(R.id.contractNoEditText)
    EditText contractNoEditText;
    @BindView(R.id.contractDayNoEditText)
    EditText contractDayNoEditText;
    @BindView(R.id.contractMonthSpinner)
    Spinner contractMonthSpinner;
    @BindView(R.id.contractYearEditText)
    EditText contractYearEditText;
    @BindView(R.id.reportDayEditText)
    EditText reportDayEditText;
    @BindView(R.id.failureDayNoEditText)
    EditText failureDayNoEditText;
    @BindView(R.id.failureMonthSpinner)
    Spinner failureMonthSpinner;
    @BindView(R.id.failureYearEditText)
    EditText failureYearEditText;
    @BindView(R.id.failureDatEditText)
    EditText failureDatEditText;
    @BindView(R.id.failureNotesEditText1)
    EditText failureNotesEditText1;
    @BindView(R.id.failureNotesEditText2)
    EditText failureNotesEditText2;
    @BindView(R.id.failureNotesEditText3)
    EditText failureNotesEditText3;
    @BindView(R.id.duration)
    EditText duration;
    @BindView(R.id.check1)
    CheckBox check1;
    @BindView(R.id.check2)
    CheckBox check2;
    @BindView(R.id.check3)
    CheckBox check3;
    @BindView(R.id.fixingDayEditText)
    EditText fixingDayEditText;
    @BindView(R.id.fixingTimeEditText)
    EditText fixingTimeEditText;
    @BindView(R.id.fixingYearEditText)
    EditText fixingYearEditText;
    @BindView(R.id.fixingMonthSpinner)
    Spinner fixingMonthSpinner;
    @BindView(R.id.fixingDayNoEditText)
    EditText fixingDayNoEditText;
    @BindView(R.id.check4)
    CheckBox check4;
    @BindView(R.id.notesEditText)
    EditText notesEditText;

    QuestionnairesInterface questionnairesInterface;
    QuestionnairesPresenter presenter;
    NEW_TASKS task;
    Category category;
    int taskID, issueID, tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure_fixing_report);
        App.setContext(this);
        ButterKnife.bind(this);
        questionnairesInterface = this;

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        RelativeLayout spinner = (RelativeLayout) findViewById(R.id.spinnerlayout);
        spinner.setVisibility(View.GONE);

        task =(NEW_TASKS) getIntent().getSerializableExtra("task");
        taskID = task.TASK_SYSKEY;
        category = (Category) getIntent().getSerializableExtra("category");
        taskID = getIntent().getIntExtra("taskId", 0);
        issueID = getIntent().getIntExtra("issueId", 0);
        tabs = getIntent().getIntExtra("tabs", 0);

        final ImageButton logout = (ImageButton) findViewById(R.id.logout);
        logout.setBackgroundResource(R.mipmap.back);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FailureFixingReportActivity.this, TaskActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Task", task);
                b.putSerializable("Category", category);
                if(tabs == 4) b.putInt("Tab", 3);
                else b.putInt("Tab",2);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });

        presenter = new QuestionnairesPresenter(questionnairesInterface, new QuestionnairesRepositoryImp());
        String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        contractMonthSpinner.setAdapter(spinnerArrayAdapter);
        permitMonthSpinner.setAdapter(spinnerArrayAdapter);
        failureMonthSpinner.setAdapter(spinnerArrayAdapter);
        fixingMonthSpinner.setAdapter(spinnerArrayAdapter);
        failureDatEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(App.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            failureDatEditText.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("موعد نهاية عمل المؤسسة");
                    mTimePicker.show();
                }
            }
        });
        fixingTimeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(App.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            fixingTimeEditText.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("موعد نهاية عمل المؤسسة");
                    mTimePicker.show();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reportNoEditText.getText().toString().equals("") || dayNoEditText.getText().toString().equals("") ||
                        yearEditText.getText().toString().equals("") || nameEditText.getText().toString().equals("") ||
                        neighborhoodEditText.getText().toString().equals("") ||
                        streetEditText.getText().toString().equals("") || besideEditText.getText().toString().equals("") ||
                        permitNoEditText.getText().toString().equals("") || permitDayNoEditText.getText().toString().equals("") ||
                        permitYearEditText.getText().toString().equals("") || contractNoEditText.getText().toString().equals("") ||
                        contractDayNoEditText.getText().toString().equals("") || contractYearEditText.getText().toString().equals("") ||
                        failureYearEditText.getText().toString().equals("") || failureDatEditText.getText().toString().equals("") ||
                        duration.getText().toString().equals("") || reportDayEditText.getText().toString().equals("") ||
                        failureDayNoEditText.getText().toString().equals("") || failureNotesEditText1.getText().toString().equals("")) {
                    Toast.makeText(FailureFixingReportActivity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();
                } else {
                    FailureFixing03_03 obj = new FailureFixing03_03();
                    obj.Report_Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) +"/"+ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition()+1))+"/"+
                            ArabicNumber(yearEditText.getText().toString());
                    obj.Report_Num = reportNoEditText.getText().toString();
                    obj.Worker_Name = nameEditText.getText().toString();
                    obj.House_ID = 6;
                    obj.NearBy = besideEditText.getText().toString();
                    obj.District = neighborhoodEditText.getText().toString();
                    obj.Street = streetEditText.getText().toString();
                    obj.License_ID = permitNoEditText.getText().toString();
                    obj.License_Date = ArabicNumber(permitDayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(permitMonthSpinner.getSelectedItemPosition() + 1)) +
                            "/" + ArabicNumber(permitYearEditText.getText().toString());
                    obj.Contract_Num = Integer.valueOf(contractNoEditText.getText().toString());
                    obj.Contract_Date = ArabicNumber(contractDayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(contractMonthSpinner.getSelectedItemPosition() + 1)) +
                            "/" + ArabicNumber(contractYearEditText.getText().toString());
                    obj.Visit_Day = reportDayEditText.getText().toString();
                    DateFormat format = new SimpleDateFormat("h:mm", Locale.ENGLISH);
                    try {
                        Date date = format.parse(failureDatEditText.getText().toString());
                        obj.Visit_Time = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    obj.Vsit_Date = ArabicNumber(failureDayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(failureMonthSpinner.getSelectedItemPosition() + 1)) +
                            "/" + ArabicNumber(failureYearEditText.getText().toString());
                    obj.Remark_1 = failureNotesEditText1.getText().toString();
                    obj.Remark_2 = failureNotesEditText2.getText().toString();
                    obj.Remark_3 = failureNotesEditText3.getText().toString();
                    obj.Time_limit = duration.getText().toString();
                    if (check1.isChecked()){
                        obj.Solve_Problem = true;
                    }
                    if (check2.isChecked()){
                        obj.Not_Solving_AfterTimeLimit = true;
                    }
                    if (check3.isChecked()){
                        obj.Solved_ByServiceGroup = true;
                    }
                    try {
                        Date date = format.parse(fixingTimeEditText.getText().toString());
                        obj.Solving_Time = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    obj.Solvng_Day = fixingDayEditText.getText().toString();
                    obj.Solving_Date = ArabicNumber(fixingDayNoEditText.getText().toString())+"/"+ArabicNumber(String.valueOf(fixingMonthSpinner.getSelectedItemPosition()+1))+
                            "/"+ArabicNumber(fixingYearEditText.getText().toString());
                    obj.Remark = notesEditText.getText().toString();
                    obj.SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(FailureFixingReportActivity.this, getString(R.string.user_id)));
                    obj.Task_ID = taskID;
                    obj.Issue_ID = issueID;
                    obj.SYSDATE_TIME = Calendar.getInstance().getTime();
                    obj.Report_Active = true;
                    obj.Addation_Num = "03-03";
                    Gson gson = new Gson();
                    String json = gson.toJson(obj);
                    presenter.questionnaires(json, "03-03","Add");
                }
            }
        });
    }


    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();


        Intent i = new Intent(FailureFixingReportActivity.this, TaskActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Task", task);
        b.putSerializable("Category", category);
        if(tabs == 4) b.putInt("Tab", 3);
        else b.putInt("Tab",2);
        i.putExtras(b);
        startActivity(i);

        finish();
    }

    @Override
    public void NotSaved(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();

    }



    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(FailureFixingReportActivity.this, TaskActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Task", task);
        b.putSerializable("Category", category);
        if(tabs == 4) b.putInt("Tab", 3);
        else b.putInt("Tab",2);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
