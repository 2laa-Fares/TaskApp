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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.Follow_up_services02_07;
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

public class DailyReportForHajServicesFollowingMnaActivity extends AppCompatActivity implements QuestionnairesInterface{

    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.waterSpinner)
    Spinner waterSpinner;
    @BindView(R.id.cleanSpinner)
    Spinner cleanSpinner;
    @BindView(R.id.cleanerFoundSpinner)
    Spinner cleanerfoundSpinner;
    @BindView(R.id.toiletCleaningSpinner)
    Spinner toiletCleaningSpinner;
    @BindView(R.id.waterAvailabilitySpinner)
    Spinner waterAvailabilitySpinner;
    @BindView(R.id.iceArrivedSpinner)
    Spinner iceArrivedSpinner;
    @BindView(R.id.iceDistributionSpinner)
    Spinner iceDistributionSpinner;
    @BindView(R.id.waterFillingSpinner)
    Spinner waterFillingSpinner;
    @BindView(R.id.waterStatusSpinner)
    Spinner waterStatusSpinner;
    @BindView(R.id.securitySpinner)
    Spinner securitySpinner;
    @BindView(R.id.safetyWorkersSpinner)
    Spinner safetyWorkersSpinner;
    @BindView(R.id.hajStateSpinner)
    Spinner hajStateSpinner;
    @BindView(R.id.hajLocationSpinner)
    Spinner hajLocationSpinner;
    @BindView(R.id.chairAvailableSpinner)
    Spinner chairAvailableSpinner;
    @BindView(R.id.sickAvailableSpinner)
    Spinner sickAvailableSpinner;
    @BindView(R.id.workerFormalSpinner)
    Spinner workerFormalSpinner;
    @BindView(R.id.securityFormalSpinner)
    Spinner securityFormalSpinner;
    @BindView(R.id.kitchenCleaningSpinner)
    Spinner kitchenCleaningSpinner;
    @BindView(R.id.safetySpinner)
    Spinner safetySpinner;
    @BindView(R.id.hajMealArriveSpinner)
    Spinner hajMealArriveSpinner;
    @BindView(R.id.mealDistributionSpinner)
    Spinner mealDistributionSpinner;
    @BindView(R.id.distributionTimeET)
    EditText distributionTimeET;
    @BindView(R.id.distributionEndTimeET)
    EditText distributionEndTimeET;
    @BindView(R.id.visitTimeEditText)
    EditText visitTimeEditText;
    @BindView(R.id.jamaratReporterSpinner)
    Spinner jamaratReporterSpinner;
    @BindView(R.id.mediaFoundSpinner)
    Spinner mediaFoundSpinner;
    @BindView(R.id.internalRadioSpinner)
    Spinner internalRadioSpinner;
    @BindView(R.id.strapsAvailabilitySpinner)
    Spinner strapsAvailabilitySpinner;
    @BindView(R.id.dayEditText)
    EditText dayEditText;
    @BindView(R.id.dayNoEditText)
    EditText dayNoEditText;
    @BindView(R.id.yearEditText)
    EditText yearEditText;
    @BindView(R.id.visitNoEditText)
    EditText visitNoEditText;
    @BindView(R.id.saveButton)
    Button saveButton;
    QuestionnairesInterface questionnairesInterface;
    QuestionnairesPresenter presenter;
    int taskID, issueID, tabs;
    NEW_TASKS task;
    Category category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report_for_haj_services_following_mna);
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
                Intent i = new Intent(DailyReportForHajServicesFollowingMnaActivity.this, TaskActivity.class);
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
        String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        String[] water = {"متوفر", "غير متوفر"};
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, water);
        waterSpinner.setAdapter(spinnerArrayAdapter2);
        String[] clean = {"نظيف", "متسخ"};
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, clean);
        cleanSpinner.setAdapter(spinnerArrayAdapter3);
        String[] cleaner = {"متواجد", "غير متواجد"};
        ArrayAdapter spinnerArrayAdapter4 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, cleaner);
        cleanerfoundSpinner.setAdapter(spinnerArrayAdapter4);
        String[] toilet = {"نظيفة", "غير نظيفة"};
        ArrayAdapter spinnerArrayAdapter5 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, toilet);
        toiletCleaningSpinner.setAdapter(spinnerArrayAdapter5);
        String[] waterAvailability = {"متوفرة", "غير متوفرة"};
        ArrayAdapter spinnerArrayAdapter6 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, waterAvailability);
        waterAvailabilitySpinner.setAdapter(spinnerArrayAdapter6);
        String[] iceArrived = {"وصل", "لم يصل"};
        ArrayAdapter spinnerArrayAdapter7 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, iceArrived);
        iceArrivedSpinner.setAdapter(spinnerArrayAdapter7);
        final String[] iceDistribution = {"تم", "لم يتم"};
        ArrayAdapter spinnerArrayAdapter8 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, iceDistribution);
        iceDistributionSpinner.setAdapter(spinnerArrayAdapter8);
        String[] waterFilling = {"تمت", "لم تتم"};
        ArrayAdapter spinnerArrayAdapter9 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, waterFilling);
        waterFillingSpinner.setAdapter(spinnerArrayAdapter9);
        String[] waterStatus = {"جيدة", "غير جيدة"};
        ArrayAdapter spinnerArrayAdapter10 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, waterStatus);
        waterStatusSpinner.setAdapter(spinnerArrayAdapter10);
        String[] security = {"متواجدون", "غير متواجدين"};
        ArrayAdapter spinnerArrayAdapter11 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, security);
        securitySpinner.setAdapter(spinnerArrayAdapter11);
        safetyWorkersSpinner.setAdapter(spinnerArrayAdapter11);
        String[] hajState = {"مكتمل", "غير مكتمل"};
        ArrayAdapter spinnerArrayAdapter12 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, hajState);
        hajStateSpinner.setAdapter(spinnerArrayAdapter12);
        String[] hajLocation = {"نعم", "لا"};
        ArrayAdapter spinnerArrayAdapter13 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, hajLocation);
        hajLocationSpinner.setAdapter(spinnerArrayAdapter13);
        String[] chairAvailable = {"متوفرة", "غير متوفرة"};
        ArrayAdapter spinnerArrayAdapter14 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, chairAvailable);
        chairAvailableSpinner.setAdapter(spinnerArrayAdapter14);
        sickAvailableSpinner.setAdapter(spinnerArrayAdapter14);
        String[] workerFormal = {"ملتزمون", "غير ملتزمين"};
        ArrayAdapter spinnerArrayAdapter15 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, workerFormal);
        workerFormalSpinner.setAdapter(spinnerArrayAdapter15);
        securityFormalSpinner.setAdapter(spinnerArrayAdapter15);
        kitchenCleaningSpinner.setAdapter(spinnerArrayAdapter3);
        safetySpinner.setAdapter(spinnerArrayAdapter2);
        final String[] hajMealArrive = {"وصلت", "لم تصل"};
        ArrayAdapter spinnerArrayAdapter16 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, hajMealArrive);
        hajMealArriveSpinner.setAdapter(spinnerArrayAdapter16);
        String[] mealDistribution = {"تم التوزيع", "لم توزع"};
        ArrayAdapter spinnerArrayAdapter17 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, mealDistribution);
        mealDistributionSpinner.setAdapter(spinnerArrayAdapter17);
        final String[] internalRadio = {"تعمل", "لا تعمل"};
        ArrayAdapter spinnerArrayAdapter18 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, internalRadio);
        internalRadioSpinner.setAdapter(spinnerArrayAdapter18);
        jamaratReporterSpinner.setAdapter(spinnerArrayAdapter11);
        mediaFoundSpinner.setAdapter(spinnerArrayAdapter11);
        strapsAvailabilitySpinner.setAdapter(spinnerArrayAdapter14);
        distributionTimeET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    time("distributionTimeET");
                }
            }
        });

        distributionEndTimeET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    time("distributionEndTimeET");
                }
            }
        });
        visitTimeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    time("visitTimeEditText");
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayEditText.getText().toString().equals("") || dayNoEditText.getText().toString().equals("")
                        || yearEditText.getText().toString().equals("") || visitNoEditText.getText().toString().equals("")
                        || visitTimeEditText.getText().toString().equals("") || distributionTimeET.getText().toString().equals("")
                        || distributionEndTimeET.getText().toString().equals("")){
                    Toast.makeText(DailyReportForHajServicesFollowingMnaActivity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();
                }else {
                    Follow_up_services02_07 obj = new Follow_up_services02_07();
                    obj.Visit_Day = dayEditText.getText().toString();
                    obj.Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) +"/"+ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition()+1))+"/"+
                            ArabicNumber(yearEditText.getText().toString());
                    obj.Visit_ID = Integer.valueOf(visitNoEditText.getText().toString());
                    DateFormat format = new SimpleDateFormat("h:mm", Locale.ENGLISH);
                    try {
                        Date date = format.parse(visitTimeEditText.getText().toString());
                        obj.Visit_Time = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    obj.Has_Water = waterSpinner.getSelectedItem().toString();
                    obj.Clean = cleanSpinner.getSelectedItem().toString();
                    obj.Cleaning_Responsible = cleanerfoundSpinner.getSelectedItem().toString();
                    obj.WC_Clean = toiletCleaningSpinner.getSelectedItem().toString();
                    obj.WC_Have_Water = waterAvailabilitySpinner.getSelectedItem().toString();
                    obj.ICE_existitance = iceArrivedSpinner.getSelectedItem().toString();
                    obj.ICE_sharing = iceDistributionSpinner.getSelectedItem().toString();
                    obj.Fill_WaterTank = waterFillingSpinner.getSelectedItem().toString();
                    obj.Water_Tank_State = waterStatusSpinner.getSelectedItem().toString();
                    obj.Security_Gards = securitySpinner.getSelectedItem().toString();
                    obj.Safety_Gardes = safetyWorkersSpinner.getSelectedItem().toString();
                    obj.PILGRIMS_Status = hajStateSpinner.getSelectedItem().toString();
                    obj.PILGRIMS_Location = hajLocationSpinner.getSelectedItem().toString();
                    obj.Moving_chairs_found = chairAvailableSpinner.getSelectedItem().toString();
                    obj.Carrying_Tools_found = sickAvailableSpinner.getSelectedItem().toString();
                    obj.Work_Member_in_uniform =workerFormalSpinner.getSelectedItem().toString();
                    obj.Gards_Member_in_uniform = securityFormalSpinner.getSelectedItem().toString();
                    obj.Kitchen_Clean = kitchenCleaningSpinner.getSelectedItem().toString();
                    obj.Safety_available = safetySpinner.getSelectedItem().toString();
                    obj.Food_To_Pilgrims = hajMealArriveSpinner.getSelectedItem().toString();
                    obj.food_Sharing = mealDistributionSpinner.getSelectedItem().toString();
                    try {
                        Date date = format.parse(distributionTimeET.getText().toString());
                        obj.Time_ToShareFood = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        Date date = format.parse(distributionEndTimeET.getText().toString());
                        obj.Last_Time_ToshareFood = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    obj.Jamarat_Reporter = jamaratReporterSpinner.getSelectedItem().toString();
                    obj.Media_existing = mediaFoundSpinner.getSelectedItem().toString();
                    obj.inner_Media_Working = internalRadioSpinner.getSelectedItem().toString();
                    obj.Awareness_Existing = strapsAvailabilitySpinner.getSelectedItem().toString();
                    obj.SYSDATE_TIME = Calendar.getInstance().getTime();
                    obj.Report_Active = true;
                    obj.SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(DailyReportForHajServicesFollowingMnaActivity.this, getString(R.string.user_id)));
                    obj.Task_ID = taskID;
                    obj.Issue_ID = issueID;
                    obj.Addation_Num = "02-07";
                    Gson gson = new Gson();
                    String json = gson.toJson(obj);
                    presenter.questionnaires(json, "02-07", "Add");
                }


            }
        });

    }

    private void time(final String et) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(App.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if (et.equals("distributionTimeET"))
                    distributionTimeET.setText(hourOfDay + ":" + minute);
                else if(et.equals("distributionEndTimeET"))
                    distributionEndTimeET.setText(hourOfDay + ":" + minute);
                else if(et.equals("visitTimeEditText"))
                    visitTimeEditText.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();

        Intent i = new Intent(DailyReportForHajServicesFollowingMnaActivity.this, TaskActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("Task", task);
        b.putSerializable("Category", category);
        if(tabs == 4) b.putInt("Tab", 3);
        else b.putInt("Tab",2);
        i.putExtras(b);
        startActivity(i);
        ((Activity) App.getContext()).finish();
    }

    @Override
    public void NotSaved(String error)  {
        Toast.makeText(this, "لم يتم أضافة الاستبيان من فضلك افحص اتصال بالأنترنت وحاول مرة آخرى", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(DailyReportForHajServicesFollowingMnaActivity.this, TaskActivity.class);
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
