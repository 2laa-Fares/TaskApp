package com.example.apit.task.view;

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
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.Follow_UP_Process01_07;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.QuestionnairesPresenter;
import com.example.apit.task.repositories.imp.QuestionnairesRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;

public class CampPreparationMnaActivity extends AppCompatActivity implements QuestionnairesInterface {

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
    @BindView(R.id.securitySpinner)
    Spinner securitySpinner;
    @BindView(R.id.safetyWorkersSpinner)
    Spinner safetyWorkerSpinner;
    @BindView(R.id.electricianSpinner)
    Spinner electricianSpinner;
    @BindView(R.id.plumberSpinner)
    Spinner plumberSpinner;
    @BindView(R.id.glailStateSpinner)
    Spinner glailStateSpinner;
    @BindView(R.id.glailNoEditText)
    EditText glailNoEditText;
    @BindView(R.id.tramsStateSpinner)
    Spinner tramsStateSpinner;
    @BindView(R.id.tramsNoEditText)
    EditText tramsNoEditText;
    @BindView(R.id.pailsStateSpinner)
    Spinner pailsStateSpinner;
    @BindView(R.id.pailsNoEditText)
    EditText pailsNoEditText;
    @BindView(R.id.wasteBagsStateSpinner)
    Spinner wasteBagsStateSpinner;
    @BindView(R.id.wasteBagsNoEditText)
    EditText wasteBagsNoEditText;
    @BindView(R.id.iceCartsStateSpinner)
    Spinner iceCartsStateSpinner;
    @BindView(R.id.iceCartsNoEditText)
    EditText iceCartsNoEditText;
    @BindView(R.id.waterDrumsStateSpinner)
    Spinner waterDrumsStateSpinner;
    @BindView(R.id.waterDrumsNoEditText)
    EditText getWaterDrumsNoEditText;
    @BindView(R.id.iceStoresStateSpinner)
    Spinner iceStoresStateSpinner;
    @BindView(R.id.iceStoresNoEditText)
    EditText iceStoresNoEditText;
    @BindView(R.id.indicativePlatesStateSpinner)
    Spinner indicativePlatesStateSpinner;
    @BindView(R.id.indicativePlatesEnoughSpinner)
    Spinner indicativePlatesEnoughSpinner;
    @BindView(R.id.numberingTentsStateSpinner)
    Spinner numberingTentsStateSpinner;
    @BindView(R.id.numberingTentsEnoughSpinner)
    Spinner numberingTentsEnoughSpinner;
    @BindView(R.id.cleaningToolsStateSpinner)
    Spinner cleaningToolsStateSpinner;
    @BindView(R.id.cleaningToolsEnoughSpinner)
    Spinner cleaningToolsEnoughSpinner;
    @BindView(R.id.healthServicesStateSpinner)
    Spinner healthServicesStateSpinner;
    @BindView(R.id.healthServicesEnoughSpinner)
    Spinner healthServicesEnoughSpinner;
    @BindView(R.id.locationWorksEditText1)
    EditText locationWorksEditText1;
    @BindView(R.id.locationWorksEditText2)
    EditText locationWorksEditText2;
    @BindView(R.id.locationWorksEditText3)
    EditText locationWorksEditText3;
    @BindView(R.id.locationWorksEditText4)
    EditText locationWorksEditText4;
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
        setContentView(R.layout.activity_camp_preparation_mna);
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
                Intent i = new Intent(CampPreparationMnaActivity.this, TaskActivity.class);
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
        final Map<String, Integer> stateMap = new HashMap<>();
        stateMap.put("جيدة", 2);
        stateMap.put("متوسطة", 1);
        stateMap.put("سيئة", 0);
        String[] state = {"جيدة", "متوسطة", "سيئة"};
        String[] enough = {"كافية", "غير كافية"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, found);
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, state);
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, enough);
        securitySpinner.setAdapter(spinnerArrayAdapter);
        safetyWorkerSpinner.setAdapter(spinnerArrayAdapter);
        electricianSpinner.setAdapter(spinnerArrayAdapter);
        plumberSpinner.setAdapter(spinnerArrayAdapter);
        glailStateSpinner.setAdapter(spinnerArrayAdapter2);
        tramsStateSpinner.setAdapter(spinnerArrayAdapter2);
        pailsStateSpinner.setAdapter(spinnerArrayAdapter2);
        wasteBagsStateSpinner.setAdapter(spinnerArrayAdapter3);
        iceCartsStateSpinner.setAdapter(spinnerArrayAdapter2);
        waterDrumsStateSpinner.setAdapter(spinnerArrayAdapter2);
        iceStoresStateSpinner.setAdapter(spinnerArrayAdapter2);
        indicativePlatesStateSpinner.setAdapter(spinnerArrayAdapter2);
        indicativePlatesEnoughSpinner.setAdapter(spinnerArrayAdapter3);
        numberingTentsStateSpinner.setAdapter(spinnerArrayAdapter2);
        numberingTentsEnoughSpinner.setAdapter(spinnerArrayAdapter3);
        cleaningToolsStateSpinner.setAdapter(spinnerArrayAdapter2);
        cleaningToolsEnoughSpinner.setAdapter(spinnerArrayAdapter3);
        healthServicesStateSpinner.setAdapter(spinnerArrayAdapter2);
        healthServicesEnoughSpinner.setAdapter(spinnerArrayAdapter3);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayEditText.getText().toString().equals("") || dayNoEditText.getText().toString().equals("") ||
                        yearEditText.getText().toString().equals("") || visitNoEditText.getText().toString().equals("") ||
                        organizerNAmeEditText.getText().toString().equals("") ||
                        glailNoEditText.getText().toString().equals("") || tramsNoEditText.getText().toString().equals("") ||
                        pailsNoEditText.getText().toString().equals("") || wasteBagsNoEditText.getText().toString().equals("") ||
                        iceCartsNoEditText.getText().toString().equals("") || getWaterDrumsNoEditText.getText().toString().equals("") ||
                        iceStoresNoEditText.getText().toString().equals("")) {
                    Toast.makeText(CampPreparationMnaActivity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();

                } else {
                    Follow_UP_Process01_07 obj = new Follow_UP_Process01_07();
                    obj.Visit_Day = dayEditText.getText().toString();
                    obj.Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition() + 1)) + "/" +
                            ArabicNumber(yearEditText.getText().toString());
                    obj.Visit_ID = Integer.valueOf(visitNoEditText.getText().toString());
                    obj.Report_Active = true;
                    obj.SYSDATE_TIME = Calendar.getInstance().getTime();
                    obj.Visit_Coordinator_Name = organizerNAmeEditText.getText().toString();
                    obj.Security_Guardes = securitySpinner.getSelectedItem().toString().equals("موجود");
                    obj.Safety_Guardes = safetyWorkerSpinner.getSelectedItem().toString().equals("موجود");
                    obj.Electrician = electricianSpinner.getSelectedItem().toString().equals("موجود");
                    obj.Technician_Plumbing = plumberSpinner.getSelectedItem().toString().equals("موجود");
                    obj.Glail_Size_2_3_Num = Integer.valueOf(glailNoEditText.getText().toString());
                    obj.Glail_Size_2_3_Status = stateMap.get(glailStateSpinner.getSelectedItem().toString());
                    obj.Trams_Num = Integer.valueOf(tramsNoEditText.getText().toString());
                    obj.Trams_Status = stateMap.get(tramsStateSpinner.getSelectedItem().toString());
                    obj.Waste_Disposal_Num = Integer.valueOf(pailsNoEditText.getText().toString());
                    obj.Waste_Disposal_Status = stateMap.get(pailsStateSpinner.getSelectedItem().toString());
                    obj.Waste_Bucket_Num = Integer.valueOf(wasteBagsNoEditText.getText().toString());
                    obj.Waste_Bucket_Enough = wasteBagsStateSpinner.getSelectedItem().toString().equals("كافية");
                    obj.Ice_Waste_Vehicles_Num = Integer.valueOf(iceCartsNoEditText.getText().toString());
                    obj.Ice_Waste_Vehicles_Status = stateMap.get(iceCartsStateSpinner.getSelectedItem().toString());
                    obj.Water_Tanks_Num = Integer.valueOf(getWaterDrumsNoEditText.getText().toString());
                    obj.Water_Tanks_Status = stateMap.get(waterDrumsStateSpinner.getSelectedItem().toString());
                    obj.Ice_Tanks_Num = Integer.valueOf(iceStoresNoEditText.getText().toString());
                    obj.Ice_Tanks_Status = stateMap.get(iceStoresStateSpinner.getSelectedItem().toString());
                    obj.Guide_Signs_Enough = indicativePlatesEnoughSpinner.getSelectedItem().toString().equals("كافية");
                    obj.Guide_Signs_Status = stateMap.get(indicativePlatesStateSpinner.getSelectedItem().toString());
                    obj.Tent_Numbering_Enough = numberingTentsEnoughSpinner.getSelectedItem().toString().equals("كافية");
                    obj.Tent_Numbering_Status = stateMap.get(numberingTentsStateSpinner.getSelectedItem().toString());
                    obj.Cleaning_Tools_Enough = cleaningToolsEnoughSpinner.getSelectedItem().toString().equals("كافية");
                    obj.Cleaning_Tools_Status = stateMap.get(cleaningToolsStateSpinner.getSelectedItem().toString());
                    obj.Health_Tools_Enough = healthServicesEnoughSpinner.getSelectedItem().toString().equals("كافية");
                    obj.Health_Tools_Status = stateMap.get(healthServicesStateSpinner.getSelectedItem().toString());
                    obj.Work_inLocation = locationWorksEditText1.getText().toString() + locationWorksEditText2.getText().toString() +
                            locationWorksEditText3.getText().toString() + locationWorksEditText4.getText().toString();
                    obj.SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(CampPreparationMnaActivity.this, getString(R.string.user_id)));
                    obj.Task_ID = taskID;
                    obj.Issue_ID = issueID;
                    obj.Addation_Num = "01-07";
                    Gson gson = new Gson();
                    String json = gson.toJson(obj);
                    presenter.questionnaires(json, "01-07", "Add");
                }


            }
        });
    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();

        Intent i = new Intent(CampPreparationMnaActivity.this, TaskActivity.class);
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
        Intent i = new Intent(CampPreparationMnaActivity.this, TaskActivity.class);
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
