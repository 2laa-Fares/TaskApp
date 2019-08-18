package com.example.apit.task.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.Follow_Up_Process01_08;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.QuestionnairesPresenter;
import com.example.apit.task.repositories.imp.QuestionnairesRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;


public class CampPreparationArafatActivity extends AppCompatActivity implements QuestionnairesInterface {

    @BindView(R.id.dayEditText)
    EditText dayEditText;
    @BindView(R.id.dayNoEditText)
    EditText dayNoEditText;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearEditText)
    EditText yearEditText;
    @BindView(R.id.visitNoEditText)
    EditText visitNoEditText;
    @BindView(R.id.organizerNameEditText)
    EditText organizerNAmeEditText;
    @BindView(R.id.supervisorSpinner)
    Spinner supervisorSpinner;
    @BindView(R.id.canvasWorkerSpinner)
    Spinner canvasWorkerSpinner;
    @BindView(R.id.securitySpinner)
    Spinner securitySpinner;
    @BindView(R.id.safetyWorkersSpinner)
    Spinner safetyWorkerSpinner;
    @BindView(R.id.electricianSpinner)
    Spinner electricianSpinner;
    @BindView(R.id.plumberSpinner)
    Spinner plumberSpinner;
    @BindView(R.id.IronBarsNoEditText)
    EditText IronBarsNoEditText;
    @BindView(R.id.IronBarsStateSpinner)
    Spinner IronBarsStateSpinner;
    @BindView(R.id.zincStateSpinner)
    Spinner zincStateSpinner;
    @BindView(R.id.zincNoEditText)
    EditText zincNoEditText;
    @BindView(R.id.canvasStateSpinner)
    Spinner canvasStateSpinner;
    @BindView(R.id.canvasNoEditText)
    EditText canvasNoEditText;
    @BindView(R.id.bakingStateSpinner)
    Spinner bakingStatSpinner;
    @BindView(R.id.bakingNoEditText)
    EditText bakingNoEditText;
    @BindView(R.id.tramsStateSpinner)
    Spinner tramsStateSpinner;
    @BindView(R.id.tramsNoEditText)
    EditText tramsNoEditText;
    @BindView(R.id.fireExtinguishersStateSpinner)
    Spinner fireExtinguishersStateSpinner;
    @BindView(R.id.fireExtinguishersNoEditText)
    EditText fireExtinguishersNoEditText;
    @BindView(R.id.firePailsStateSpinner)
    Spinner firePailsStateSpinner;
    @BindView(R.id.firePailsNoEditText)
    EditText firePailsNoEditText;
    @BindView(R.id.wastePailsStateSpinner)
    Spinner wastePailsStateSpinner;
    @BindView(R.id.wastePailsNoEditText)
    EditText wastePailsNoEditText;
    @BindView(R.id.waterDrumsStateSpinner)
    Spinner waterDrumsStateSpinner;
    @BindView(R.id.waterDrumsNoEditText)
    EditText waterDrumsNoEditText;
    @BindView(R.id.emergencyDoorsStateSpinner)
    Spinner emergencyDoorsStateSpinner;
    @BindView(R.id.emergencyDoorsNoEditText)
    EditText emergencyDoorsNoEditText;
    @BindView(R.id.alshbokEditText)
    EditText alshbokEditText;
    @BindView(R.id.courseNoEditText)
    EditText courseNoEditText;
    @BindView(R.id.stripesEditText)
    EditText stripesEditText;
    @BindView(R.id.doorsEditText)
    EditText doorsEditText;
    @BindView(R.id.tapsEditText)
    EditText tapsEditText;
    @BindView(R.id.lightEditText)
    EditText lightEditText;
    @BindView(R.id.notesEditText)
    EditText notesEditText;
    @BindView(R.id.manhalCoverEditText)
    EditText manhalCoverEditText;
    @BindView(R.id.wasteRoomEditText)
    EditText wasteRoomEditText;
    @BindView(R.id.streetNameEditText)
    EditText streetNameEditText;
    @BindView(R.id.phoneNoEditText)
    EditText phoneNoEditText;
    @BindView(R.id.officeLightEditText)
    EditText officeLightEditText;
    @BindView(R.id.affiliatedAuthorityEditText)
    EditText affiliatedAuthorityEditText;
    @BindView(R.id.wasteEditText)
    EditText wasteEditText;
    @BindView(R.id.notes)
    EditText notes;
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
        setContentView(R.layout.activity_camp_preparation_arafat);
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
                Intent i = new Intent(CampPreparationArafatActivity.this, TaskActivity.class);
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
        ArrayAdapter spinnerArrayAdapter1 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        monthSpinner.setAdapter(spinnerArrayAdapter1);
        String[] found = {"موجود", "غير موجود"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, found);
        supervisorSpinner.setAdapter(spinnerArrayAdapter);
        canvasWorkerSpinner.setAdapter(spinnerArrayAdapter);
        securitySpinner.setAdapter(spinnerArrayAdapter);
        safetyWorkerSpinner.setAdapter(spinnerArrayAdapter);
        electricianSpinner.setAdapter(spinnerArrayAdapter);
        plumberSpinner.setAdapter(spinnerArrayAdapter);
        String[] status= {"سيئة", "متوسطة", "جيدة"};
        ArrayAdapter spinnerStatuesAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, status);
        IronBarsStateSpinner.setAdapter(spinnerStatuesAdapter);
        zincStateSpinner.setAdapter(spinnerStatuesAdapter);
        canvasStateSpinner.setAdapter(spinnerStatuesAdapter);
        bakingStatSpinner.setAdapter(spinnerStatuesAdapter);
        tramsStateSpinner.setAdapter(spinnerStatuesAdapter);
        fireExtinguishersStateSpinner.setAdapter(spinnerStatuesAdapter);
        firePailsStateSpinner.setAdapter(spinnerStatuesAdapter);
        wastePailsStateSpinner.setAdapter(spinnerStatuesAdapter);
        waterDrumsStateSpinner.setAdapter(spinnerStatuesAdapter);
        emergencyDoorsStateSpinner.setAdapter(spinnerStatuesAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(dayEditText.getText()) || TextUtils.isEmpty(dayNoEditText.getText()) || TextUtils.isEmpty(yearEditText.getText())
                        || TextUtils.isEmpty(visitNoEditText.getText()) || TextUtils.isEmpty(organizerNAmeEditText.getText()) || TextUtils.isEmpty(IronBarsNoEditText.getText())
                        || TextUtils.isEmpty(zincNoEditText.getText()) || TextUtils.isEmpty(canvasNoEditText.getText()) || TextUtils.isEmpty(bakingNoEditText.getText())
                        || TextUtils.isEmpty(tramsNoEditText.getText()) || TextUtils.isEmpty(fireExtinguishersNoEditText.getText()) || TextUtils.isEmpty(firePailsNoEditText.getText())
                        || TextUtils.isEmpty(wastePailsNoEditText.getText()) || TextUtils.isEmpty(waterDrumsNoEditText.getText()) || TextUtils.isEmpty(emergencyDoorsNoEditText.getText())
                        || TextUtils.isEmpty(alshbokEditText.getText()) || TextUtils.isEmpty(courseNoEditText.getText()) || TextUtils.isEmpty(stripesEditText.getText())
                        || TextUtils.isEmpty(doorsEditText.getText()) || TextUtils.isEmpty(tapsEditText.getText()) || TextUtils.isEmpty(lightEditText.getText())
                        || TextUtils.isEmpty(notes.getText()) || TextUtils.isEmpty(manhalCoverEditText.getText()) || TextUtils.isEmpty(wasteRoomEditText.getText())
                        || TextUtils.isEmpty(streetNameEditText.getText()) || TextUtils.isEmpty(phoneNoEditText.getText()) || TextUtils.isEmpty(officeLightEditText.getText())
                        || TextUtils.isEmpty(affiliatedAuthorityEditText.getText()) || TextUtils.isEmpty(wasteEditText.getText()) || TextUtils.isEmpty(notes.getText())){

                    Toast.makeText(CampPreparationArafatActivity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();
                }else{
                    Follow_Up_Process01_08 obj = new Follow_Up_Process01_08();
                    obj.Visit_Day = dayEditText.getText().toString();
                    int month = monthSpinner.getSelectedItemPosition()+1;
                    obj.Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) +"/"+ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition()+1))+"/"+
                            ArabicNumber(yearEditText.getText().toString());
                    obj.Visit_ID = Integer.valueOf(visitNoEditText.getText().toString());
                    obj.Visit_Coordinator_Name = organizerNAmeEditText.getText().toString();
                    obj.supervisor = supervisorSpinner.getSelectedItem().equals("موجود");
                    obj.Tents_Workers = canvasWorkerSpinner.getSelectedItem().equals("موجود");
                    obj.Security_Guards = securitySpinner.getSelectedItem().equals("موجود");
                    obj.Safety_Workers = safetyWorkerSpinner.getSelectedItem().equals("موجود");
                    obj.Electrician = electricianSpinner.getSelectedItem().equals("موجود");
                    obj.Plumber = plumberSpinner.getSelectedItem().equals("موجود");
                    obj.Iron_bars_Num = Integer.valueOf(IronBarsNoEditText.getText().toString());
                    obj.Iron_Bars_Status = IronBarsStateSpinner.getSelectedItemPosition();
                    obj.Zink_Num = Integer.valueOf(zincNoEditText.getText().toString());
                    obj.Zink_Satuts = zincStateSpinner.getSelectedItemPosition();
                    obj.Tents_Num = Integer.valueOf(canvasNoEditText.getText().toString());
                    obj.Tents_Status = canvasStateSpinner.getSelectedItemPosition();
                    obj.Baking_Num = Integer.valueOf(bakingNoEditText.getText().toString());
                    obj.Baking_Status = bakingStatSpinner.getSelectedItemPosition();
                    obj.Trams_Num = Integer.valueOf(tramsNoEditText.getText().toString());
                    obj.Trams_Status = tramsStateSpinner.getSelectedItemPosition();
                    obj.Fire_Extinguishers_Num = Integer.valueOf(fireExtinguishersNoEditText.getText().toString());
                    obj.Fire_Extinguishers_Status = fireExtinguishersStateSpinner.getSelectedItemPosition();
                    obj.Stall_Fire_Num = Integer.valueOf(firePailsNoEditText.getText().toString());
                    obj.Stall_Fire_Status = firePailsStateSpinner.getSelectedItemPosition();
                    obj.Waste_Disposal_Num = Integer.valueOf(wastePailsNoEditText.getText().toString());
                    obj.Waste_Disposal_Status = wastePailsStateSpinner.getSelectedItemPosition();
                    obj.Water_Drums_Num = Integer.valueOf(waterDrumsNoEditText.getText().toString());
                    obj.Water_Drums_Status = waterDrumsStateSpinner.getSelectedItemPosition();
                    obj.Emergency_Doors_Num = Integer.valueOf(emergencyDoorsNoEditText.getText().toString());
                    obj.Emergency_Doors_Status = emergencyDoorsStateSpinner.getSelectedItemPosition();
                    obj.Courses_Num = Integer.valueOf(courseNoEditText.getText().toString());
                    obj.Stripes_Num = Integer.valueOf(stripesEditText.getText().toString());
                    obj.Doors = Integer.valueOf(doorsEditText.getText().toString());
                    obj.Lighting = Integer.valueOf(lightEditText.getText().toString());
                    obj.Taps = Integer.valueOf(tapsEditText.getText().toString());
                    obj.Windows = Integer.valueOf(alshbokEditText.getText().toString());
                    obj.Manhal_Cover = Integer.valueOf(manhalCoverEditText.getText().toString());
                    obj.Notes = notesEditText.getText().toString();
                    obj.Street_Name = streetNameEditText.getText().toString();
                    obj.Waste_Room = wasteRoomEditText.getText().toString();
                    obj.Office_lighting = officeLightEditText.getText().toString();
                    obj.Phone_Number = phoneNoEditText.getText().toString();
                    obj.Waste = wasteEditText.getText().toString();
                    obj.Affiliate = affiliatedAuthorityEditText.getText().toString();
                    obj.Remarks = notes.getText().toString();

                    obj.Addation_Num = "01-08";
                    obj.House_ID = 1;
                    obj.SYSDATE_TIME = Calendar.getInstance().getTime();
                    obj.Report_Active = true;
                    obj.SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(CampPreparationArafatActivity.this, getString(R.string.user_id)));
                    obj.Task_ID = taskID;
                    obj.Issue_ID = issueID;
                    obj.Addation_Num = "01-08";
                    Gson gson = new Gson();
                    String json = gson.toJson(obj);
                    presenter.questionnaires(json, "01-08", "Add");
                }
            }
        });


    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();

        Intent i = new Intent(CampPreparationArafatActivity.this, TaskActivity.class);
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
        Intent i = new Intent(CampPreparationArafatActivity.this, TaskActivity.class);
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
