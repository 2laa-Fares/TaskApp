package com.example.apit.task.view;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.DailyDetection02_03;
import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.FormsPresenter;
import com.example.apit.task.presenter.QuestionnairesPresenter;
import com.example.apit.task.repositories.imp.QuestionnairesRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;

public class DailyDetection02_03Activity extends AppCompatActivity implements HousingInterface, QuestionnairesInterface {

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
    @BindView(R.id.nationalityEditText)
    Spinner nationalityEditText;
    @BindView(R.id.buildingNoTV)
    TextView buildingNoTV;
    @BindView(R.id.guardFoundSpinner)
    Spinner guardFoundSpinner;
    @BindView(R.id.guardNationalityTV)
    TextView guardNationalityTV;
    @BindView(R.id.buildingCleaningSpinner)
    Spinner buildingCleaningSpinner;
    @BindView(R.id.cleaningNotesET)
    EditText cleaningNotesET;
    @BindView(R.id.lightDamageSpinner)
    Spinner lightDamageSpinner;
    @BindView(R.id.lightDamageTypeET)
    EditText lightDamageTypeET;
    @BindView(R.id.lightDamageFloorNoET)
    EditText lightDamageFloorNoET;
    @BindView(R.id.lightDamageMainEntranceSpinner)
    Spinner lightDamageMainEntranceSpinner;
    @BindView(R.id.lightDamageRoomET)
    EditText lightDamageRoomET;
    @BindView(R.id.lightDamagePassageET)
    EditText lightDamagePassageET;
    @BindView(R.id.lightDamageKitchenET)
    EditText lightDamageKitchenET;
    @BindView(R.id.lightDamageToiletET)
    EditText lightDamageToiletET;
    @BindView(R.id.acDamageTypeET)
    EditText acDamageTypeET;
    @BindView(R.id.acDamageSpinner)
    Spinner acDamageSpinner;
    @BindView(R.id.acDamageRoomNoET)
    EditText acDamageRoomNoET;
    @BindView(R.id.acDamageFloorNoET)
    EditText acDamageFloorNoET;
    @BindView(R.id.refrigeratorDamageTypeET)
    EditText refrigeratorDamageTypeET;
    @BindView(R.id.refrigeratorDamageSpinner)
    Spinner refrigeratorDamageSpinner;
    @BindView(R.id.refrigeratorDamageFloorNoET)
    EditText refrigeratorDamageFloorNoET;
    @BindView(R.id.refrigeratorDamageMainEntranceSpinner)
    Spinner refrigeratorDamageMainEntranceSpinner;
    @BindView(R.id.zmzmWaterFoundSpinner)
    Spinner zmzmWaterFoundSpinner;
    @BindView(R.id.refrigeratorFoundMainEntranceSpinner)
    Spinner refrigeratorFoundMainEntranceSpinner;
    @BindView(R.id.toiletStateSpinner)
    Spinner toiletStateSpinner;
    @BindView(R.id.toiletDamageTypeET)
    EditText toiletDamageTypeET;
    @BindView(R.id.toiletDamageSpinner)
    Spinner toiletDamageSpinner;
    @BindView(R.id.toiletDamageLocationET)
    EditText toiletDamageLocationET;
    @BindView(R.id.kitchenStateSpinner)
    Spinner kitchenStateSpinner;
    @BindView(R.id.kitchenDamageTypeET)
    EditText kitchenDamageTypeET;
    @BindView(R.id.kitchenDamageSpinner)
    Spinner kitchenDamageSpinner;
    @BindView(R.id.kitchenDamageLocationET)
    EditText kitchenDamageLocationET;
    @BindView(R.id.hajNoSpinner)
    Spinner hajNoSpinner;
    @BindView(R.id.hajIncreaseET)
    EditText hajIncreaseET;
    @BindView(R.id.saveAction)
    Button save;
    @BindView(R.id.failureFixingCheckBox)
    CheckBox failureFixingCheckBox;

    HousingInterface housingInterface;
    FormsPresenter presenter;
    HOUSING_BUILDING_DATA data;
    NATIONALITY[] nationalities;
    QuestionnairesInterface questionnairesInterface;
    QuestionnairesPresenter presenter2;
    NEW_TASKS task ;
    Category category;
    int taskID, issueID, houseID, tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_detection);
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
        houseID = task.HOUSING_SYSKEY;
        tabs = getIntent().getIntExtra("tabs", 0);

        final ImageButton logout = (ImageButton) findViewById(R.id.logout);
        logout.setBackgroundResource(R.mipmap.back);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DailyDetection02_03Activity.this, TaskActivity.class);
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

        housingInterface = this;
        questionnairesInterface = this;

        presenter2 = new QuestionnairesPresenter(this, new QuestionnairesRepositoryImp());
        presenter = new FormsPresenter(housingInterface);
        presenter.getHouses(houseID);
        presenter.getNationality();
        String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        String[] array1 = {"متواجد", "غير متواجد"};
        String[] array3 = {"جيدة", "سيئة"};
        String[] array4 = {"يوجد عطل", "لا يوجد عطل"};
        String[] array5 = {"نعم", "لا"};
        String[] array6 = {"متوفرة", "غير متوفرة"};
        String[] array7 = {"نظيفة", "سيئة"};
        ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, array1);
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, array3);
        ArrayAdapter spinnerArrayAdapter4 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, array4);
        ArrayAdapter spinnerArrayAdapter5 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, array5);
        ArrayAdapter spinnerArrayAdapter6 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, array6);
        ArrayAdapter spinnerArrayAdapter7 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, array7);
        guardFoundSpinner.setAdapter(spinnerArrayAdapter1);
        buildingCleaningSpinner.setAdapter(spinnerArrayAdapter3);
        lightDamageSpinner.setAdapter(spinnerArrayAdapter4);
        lightDamageMainEntranceSpinner.setAdapter(spinnerArrayAdapter5);
        acDamageSpinner.setAdapter(spinnerArrayAdapter4);
        refrigeratorDamageSpinner.setAdapter(spinnerArrayAdapter4);
        refrigeratorDamageMainEntranceSpinner.setAdapter(spinnerArrayAdapter5);
        refrigeratorFoundMainEntranceSpinner.setAdapter(spinnerArrayAdapter6);
        zmzmWaterFoundSpinner.setAdapter(spinnerArrayAdapter6);
        toiletStateSpinner.setAdapter(spinnerArrayAdapter7);
        toiletDamageSpinner.setAdapter(spinnerArrayAdapter4);
        kitchenDamageSpinner.setAdapter(spinnerArrayAdapter4);
        kitchenStateSpinner.setAdapter(spinnerArrayAdapter7);
        hajNoSpinner.setAdapter(spinnerArrayAdapter5);
        timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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
                            timeEditText.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("موعد نهاية عمل المؤسسة");
                    mTimePicker.show();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyDetection02_03 obj = new DailyDetection02_03();
                if (dayEditText.getText().toString().equals("") || dayNoEditText.getText().toString().equals("") ||
                        yearEditText.getText().toString().equals("") || timeEditText.getText().toString().equals("")) {
                    Toast.makeText(DailyDetection02_03Activity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();
                } else {
                    obj.Visit_Date = dayEditText.getText().toString();
                    obj.Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition() + 1)) + "/" +
                            ArabicNumber(yearEditText.getText().toString());
                    obj.Visit_Time = timeEditText.getText().toString();
                    for (NATIONALITY key : nationalities) {
                        if (nationalityEditText.getSelectedItem().toString().equals(key.ADESC)) {
                            obj.PILGRIMS_Nationality = key.SIKEY;
                            break;
                        }
                    }
                    obj.House_ID = data.HOUSING_SYSKEY;
                    if (guardFoundSpinner.getSelectedItem().toString().equals("متواجد")) {
                        obj.Guard_InPlace = true;
                    } else {
                        obj.Guard_InPlace = false;
                    }
                    for (NATIONALITY key : nationalities) {
                        if (guardNationalityTV.getText().toString().equals(key.ADESC)) {
                            obj.Guard_Nationality = key.SIKEY;
                            break;
                        }
                    }
                    if (buildingCleaningSpinner.getSelectedItem().toString().equals("جيدة")) {
                        obj.Place_Clean = true;
                    } else {
                        obj.Place_Clean = false;
                    }
                    obj.Cleanng_Notes = cleaningNotesET.getText().toString();
                    if (lightDamageSpinner.getSelectedItem().toString().equals("يوجد عطل")) {
                        obj.lightening = true;
                    } else {
                        obj.lightening = false;
                    }
                    obj.lightening_Notes = lightDamageTypeET.getText().toString();
                    obj.L_Damage_Floor_Num = lightDamageFloorNoET.getText().toString();
                    if (lightDamageMainEntranceSpinner.getSelectedItem().toString().equals("نعم")) {
                        obj.L_Damage_EnteryOfHouse = true;
                    } else {
                        obj.L_Damage_EnteryOfHouse = false;
                    }
                    obj.L_Damage_Path_num = lightDamagePassageET.getText().toString();
                    obj.L_Damage_bathroom_Num = lightDamageToiletET.getText().toString();
                    obj.L_Damage_Kitchen_Num = lightDamageKitchenET.getText().toString();
                    obj.L_Damage_Room_Num = lightDamageRoomET.getText().toString();
                    if (acDamageSpinner.getSelectedItem().toString().equals("يوجد عطل")) {
                        obj.AirConditioning_Damage = true;
                    } else {
                        obj.AirConditioning_Damage = false;
                    }
                    obj.AirConditioning_DamageNote = acDamageTypeET.getText().toString();
                    obj.A_Damage_FloorNum = acDamageFloorNoET.getText().toString();
                    obj.A_Damage_Room_Num = acDamageRoomNoET.getText().toString();
                    if (refrigeratorDamageSpinner.getSelectedItem().toString().equals("يوجد عطل")) {
                        obj.Koldair_Damage = true;
                    } else {
                        obj.Koldair_Damage = false;
                    }
                    obj.Koldair_Damage_Notes = refrigeratorDamageTypeET.getText().toString();
                    if (refrigeratorDamageMainEntranceSpinner.getSelectedItem().toString().equals("نعم")) {
                        obj.K_Damage_EntryOfHouse = true;
                    } else {
                        obj.K_Damage_EntryOfHouse = false;
                    }
                    obj.K_Damage_FloorNum = refrigeratorDamageFloorNoET.getText().toString();
                    if (refrigeratorFoundMainEntranceSpinner.getSelectedItem().toString().equals("متوفرة")) {
                        obj.Entry_OfHouse = true;
                    } else {
                        obj.Entry_OfHouse = false;
                    }
                    obj.Zamzam_Water = zmzmWaterFoundSpinner.getSelectedItem().toString().equals("متوفرة");
                    obj.WC_Clean = toiletStateSpinner.getSelectedItem().toString().equals("نظيفة");
                    obj.WC_Damage = toiletDamageSpinner.getSelectedItem().toString().equals("يوجد عطل");
                    obj.WC_Damage_Type = toiletDamageTypeET.getText().toString();
                    obj.WC_Damage_Palce = toiletDamageLocationET.getText().toString();
                    obj.Kitchen_Clean = kitchenStateSpinner.getSelectedItem().toString().equals("نظيفة");
                    obj.Kitchen_Damage = kitchenDamageSpinner.getSelectedItem().toString().equals("يوجد عطل");
                    obj.Kitchen_DamageType = kitchenDamageTypeET.getText().toString();
                    obj.Kitchen_Damage_Place = kitchenDamageLocationET.getText().toString();
                    obj.Total_Pilgrims_Typical = hajNoSpinner.getSelectedItem().toString().equals("نعم");
                    if (!hajIncreaseET.getText().toString().equals(""))
                        obj.Increasing_Num = Integer.valueOf(hajIncreaseET.getText().toString());
                    obj.Report_Active = true;
                    obj.SYSDATE_TIME = Calendar.getInstance().getTime();
                    obj.SYSUSERKEY = PrefUtils.getKeys(DailyDetection02_03Activity.this, getString(R.string.user_id));
                    obj.Task_ID = taskID;
                    obj.Issue_ID = issueID;
                    obj.Addation_Num = "02-03";
                    Gson gson = new Gson();
                    String objS = gson.toJson(obj);
                    presenter2.questionnaires(objS, "02-03", "Add");

                }
            }
        });

    }


    @Override
    public void houses(HOUSING_BUILDING_DATA housing_building) {
        if (housing_building != null) {
            data = housing_building;
            buildingNoTV.setText(housing_building.HOUSING_BUILDING_ID);
            guardNationalityTV.setText(housing_building.Gard_Nationality);
        }
    }

    @Override
    public void nationalities(NATIONALITY[] nationalities) {
        if (nationalities != null) {
            this.nationalities = nationalities;
            List<String> nationality = new ArrayList<>();
            for (NATIONALITY nationality1 : nationalities) {
                nationality.add(nationality1.ADESC);
            }
            if (nationality.size() != 0) {
                ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, nationality);
                nationalityEditText.setAdapter(spinnerArrayAdapter1);
            }
        }else {
            presenter.getNationality();
        }
    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();
        if (failureFixingCheckBox.isChecked()) {
            Intent intent = new Intent(this, FailureFixingReportActivity.class);
            intent.putExtra("task", task);
            intent.putExtra("category", category);
            intent.putExtra("issueId", issueID);
            intent.putExtra("tabs", tabs);
            startActivity(intent);
            ((Activity) App.getContext()).finish();
        } else {

            Intent i = new Intent(DailyDetection02_03Activity.this, TaskActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("Task", task);
            b.putSerializable("Category", category);
            if(tabs == 4) b.putInt("Tab", 3);
            else b.putInt("Tab",2);
            i.putExtras(b);
            startActivity(i);
            ((Activity) App.getContext()).finish();
        }
    }

    @Override
    public void NotSaved(String error) {
        Toast.makeText(this, "لم يتم أضافة الاستبيان من فضلك افحص اتصال بالأنترنت وحاول مرة آخرى", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(DailyDetection02_03Activity.this, TaskActivity.class);
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
