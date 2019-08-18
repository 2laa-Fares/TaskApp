package com.example.apit.task.view;

import android.app.Activity;
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
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.Stocking_Data03_07;
import com.example.apit.task.presenter.QuestionnairesPresenter;
import com.example.apit.task.repositories.imp.QuestionnairesRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;

public class CampInventoryActivity extends AppCompatActivity implements QuestionnairesInterface{

    @BindView(R.id.dayEditText)
    EditText dayEditText;
    @BindView(R.id.dayNoEditText)
    EditText dayNoEditText;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearEditText)
    EditText yearEditText;
    @BindView(R.id.cuttersNoET)
    EditText cuttersNoET;
    @BindView(R.id.cleaningLevelET)
    EditText cleaningLevelET;
    @BindView(R.id.tentsNoET)
    EditText tentsNoET;
    @BindView(R.id.fireExtinguishersNoET)
    EditText fireExtinguishersNoET;
    @BindView(R.id.toiletsNoET)
    EditText toiletsNoET;
    @BindView(R.id.ACRemotesNoET)
    EditText ACRemotesNoET;
    @BindView(R.id.CampExitsNoET)
    EditText CampExitsNoET;
    @BindView(R.id.save)
    Button save;
    QuestionnairesPresenter presenter;
    QuestionnairesInterface questionnairesInterface;
    int taskID, issueID, tabs;
    NEW_TASKS task;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_inventory);
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
                Intent i = new Intent(CampInventoryActivity.this, TaskActivity.class);
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayEditText.getText().toString().equals("") || dayNoEditText.getText().toString().equals("") ||
                        yearEditText.getText().toString().equals("") || cuttersNoET.getText().toString().equals("") ||
                        tentsNoET.getText().toString().equals("") || fireExtinguishersNoET.getText().toString().equals("") ||
                        toiletsNoET.getText().toString().equals("") || ACRemotesNoET.getText().toString().equals("") ||
                        CampExitsNoET.getText().toString().equals("")){
                    Toast.makeText(CampInventoryActivity.this, "برجاء ادخال البيانات كاملة", Toast.LENGTH_LONG).show();

                }else {
                    Stocking_Data03_07 obj = new Stocking_Data03_07();
                    obj.Visit_Day = dayEditText.getText().toString();
                    obj.Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) +"/"+ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition()+1))+"/"+
                            ArabicNumber(yearEditText.getText().toString());
                    obj.Partition_Num = Integer.valueOf(cuttersNoET.getText().toString());
                    obj.Tents_Num = Integer.valueOf(tentsNoET.getText().toString());
                    obj.Fire_Extinguishers_Num = Integer.valueOf(fireExtinguishersNoET.getText().toString());
                    obj.WC_Num = Integer.valueOf(toiletsNoET.getText().toString());
                    obj.Ar_Conditioning_RemotC_Num = Integer.valueOf(ACRemotesNoET.getText().toString());
                    obj.Camp_Exit_Places_num = Integer.valueOf(CampExitsNoET.getText().toString());
                    obj.SYSDATE_TIME = Calendar.getInstance().getTime();
                    obj.Report_Active = true;
                    obj.SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(CampInventoryActivity.this, getString(R.string.user_id)));
                    obj.Task_ID = taskID;
                    obj.Issue_ID = issueID;
                    obj.Addation_Num = "03-07";
                    Gson gson = new Gson();
                    String json = gson.toJson(obj);
                    presenter.questionnaires(json, "03-07", "Add");
                }
            }
        });
    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(this, "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();

        Intent i = new Intent(CampInventoryActivity.this, TaskActivity.class);
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
    public void NotSaved(String error) {
        Toast.makeText(this, "لم يتم أضافة الاستبيان من فضلك افحص اتصال بالأنترنت وحاول مرة آخرى", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(CampInventoryActivity.this, TaskActivity.class);
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

