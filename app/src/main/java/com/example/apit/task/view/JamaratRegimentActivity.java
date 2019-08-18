package com.example.apit.task.view;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
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

public class JamaratRegimentActivity extends AppCompatActivity implements QuestionnairesInterface{

    @BindView(R.id.dayEditText)
    EditText dayEditText;
    @BindView(R.id.dayNoEditText)
    EditText dayNoEditText;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearEditText)
    EditText yearEditText;
    @BindView(R.id.timeEditText)
    EditText timeEditText;
    @BindView(R.id.responsibleEditText)
    EditText responsibleEditText;
    @BindView(R.id.officeLocationEditText)
    EditText officeLocationEditText;
    @BindView(R.id.squareNoEditText)
    EditText squareNoEditText;
    @BindView(R.id.delegateEditText)
    EditText delegateEditText;
    @BindView(R.id.hajNoEditText)
    EditText hajNoEditText;
    @BindView(R.id.hajNoWrittenEditText)
    EditText hajNoWrittenEditText;
    @BindView(R.id.save)
    Button save;
    QuestionnairesInterface questionnairesInterface;
    QuestionnairesPresenter presenter;
    int taskID, issueID, tabs;
    NEW_TASKS task;
    Category category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamarat_regiment);
        App.setContext(this);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        RelativeLayout spinner = (RelativeLayout) findViewById(R.id.spinnerlayout);
        spinner.setVisibility(View.GONE);

        task =(NEW_TASKS) getIntent().getSerializableExtra("task");
        taskID = task.TASK_SYSKEY;
        category = (Category) getIntent().getSerializableExtra("category");
        issueID = getIntent().getIntExtra("issueId", 0);
        tabs = getIntent().getIntExtra("tabs", 0);

        final ImageButton logout = (ImageButton) findViewById(R.id.logout);
        logout.setBackgroundResource(R.mipmap.back);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JamaratRegimentActivity.this, TaskActivity.class);
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

        questionnairesInterface = this;
        presenter = new QuestionnairesPresenter(questionnairesInterface, new QuestionnairesRepositoryImp());
        final String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        monthSpinner.setAdapter(spinnerArrayAdapter);

        timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
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
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayEditText.getText().toString().equals("") || dayNoEditText.getText().toString().equals("") ||
                        yearEditText.getText().toString().equals("") || timeEditText.getText().toString().equals("") ||
                        responsibleEditText.getText().toString().equals("") || officeLocationEditText.getText().toString().equals("") ||
                        squareNoEditText.getText().toString().equals("") || delegateEditText.getText().toString().equals("") ||
                        hajNoEditText.getText().toString().equals("") || hajNoWrittenEditText.getText().toString().equals("")){
                    Toast.makeText(JamaratRegimentActivity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();

                }else {
                    JamaratRegiment01_09 obj = new JamaratRegiment01_09();
                    obj.Visit_Day = dayEditText.getText().toString();
                    obj.Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) +"/"+ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition()+1))+"/"+
                            ArabicNumber(yearEditText.getText().toString());
                    DateFormat format = new SimpleDateFormat("h:mm", Locale.ENGLISH);
                    try {
                        Date date = format.parse(timeEditText.getText().toString());
                        obj.Visit_Time = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    obj.Responsible_NAme = responsibleEditText.getText().toString();
                    obj.Office_Location = officeLocationEditText.getText().toString();
                    obj.Block_Num = squareNoEditText.getText().toString();
                    obj.Mandoob_NAme = delegateEditText.getText().toString();
                    obj.Num_Of_Pilgrims = Integer.valueOf(hajNoEditText.getText().toString());
                    obj.Num_OF_PilgrimsT = hajNoWrittenEditText.getText().toString();
                    obj.SYSDATE_TIME = Calendar.getInstance().getTime();
                    obj.Report_Active = true;
                    obj.SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(JamaratRegimentActivity.this, getString(R.string.user_id)));
                    obj.Task_ID = taskID;
                    obj.Issue_ID = issueID;
                    obj.Addation_Num = "01-09";
                    Gson gson = new Gson();
                    String json = gson.toJson(obj);
                    presenter.questionnaires(json, "01-09", "Add");
                }
            }
        });
    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();

        Intent i = new Intent(JamaratRegimentActivity.this, TaskActivity.class);
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
        Toast.makeText(this, "لم يتم أضافة الاستبيان من فضلك افحص اتصال بالأنترنت وحاول مرة آخرى", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(JamaratRegimentActivity.this, TaskActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Task", task);
        b.putSerializable("Category", category);
        if (tabs == 4) b.putInt("Tab", 3);
        else b.putInt("Tab", 2);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
