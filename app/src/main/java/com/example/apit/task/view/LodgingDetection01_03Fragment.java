package com.example.apit.task.view;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.Detection_of_housing01_03;
import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.FormsPresenter;
import com.example.apit.task.presenter.QuestionnairesPresenter;
import com.example.apit.task.repositories.imp.QuestionnairesRepositoryImp;
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

public class LodgingDetection01_03Fragment extends Fragment implements HousingInterface, QuestionnairesInterface {

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
    @BindView(R.id.buildingLocationEditText)
    EditText buildingLocationEditText;
    @BindView(R.id.buildingNoET)
    TextView buildingNoET;
    @BindView(R.id.hajNationalityEditText)
    Spinner hajNationalityEditText;
    @BindView(R.id.permitNoEditText)
    EditText permitNoEditText;
    @BindView(R.id.distanceFromMainStreetET)
    EditText distanceFromMainStreetET;
    @BindView(R.id.distanceFromSacredET)
    EditText distanceFromSacredET;
    @BindView(R.id.busAvailabilitySpinner)
    Spinner busAvailabilitySpinner;
    @BindView(R.id.slopeFoundSpinner)
    Spinner slopeFoundSpinner;
    @BindView(R.id.slopeTypeEditText)
    EditText slopeTypeEditText;
    @BindView(R.id.buildingTypeSpinner)
    Spinner buildingTypeSpinner;
    @BindView(R.id.permitCopySpinner)
    Spinner permitCopySpinner;
    @BindView(R.id.lightDamageToiletET)
    EditText lightDamageToiletET;
    @BindView(R.id.floorsRentedET)
    EditText floorsRentedET;
    @BindView(R.id.roomsNoET)
    EditText roomsNoET;
    @BindView(R.id.kitchensNoET)
    EditText kitchensNoET;
    @BindView(R.id.fansNoET)
    EditText fansNoET;
    @BindView(R.id.toiletsNoET)
    EditText toiletsNoET;
    @BindView(R.id.ACStateSpinner)
    Spinner ACStateSpinner;
    @BindView(R.id.refrigeratorsNoET)
    EditText refrigeratorsNoET;
    @BindView(R.id.expirationDateEditText)
    EditText expirationDateEditText;
    @BindView(R.id.fireExtinguishersNoEditText)
    EditText fireExtinguishersNoEditText;
    @BindView(R.id.wastePailCountET)
    EditText wastePailCountET;
    @BindView(R.id.sandPailCountET)
    EditText sandPailCountET;
    @BindView(R.id.elevatorWidthET)
    EditText elevatorWidthET;
    @BindView(R.id.elevatorFoundSpinner)
    Spinner elevatorFoundSpinner;
    @BindView(R.id.lightEnoughSpinner)
    Spinner lightEnoughSpinner;
    @BindView(R.id.naturalLightEnoughSpinner)
    Spinner naturalLightEnoughSpinner;
    @BindView(R.id.cleaningToolsEnoughSpinner)
    Spinner cleaningToolsEnoughSpinner;
    @BindView(R.id.cleaningToolsFoundSpinner)
    Spinner cleaningToolsFoundSpinner;
    @BindView(R.id.bedsTypesSpinner)
    Spinner bedsTypesSpinner;
    @BindView(R.id.bedsCountET)
    EditText bedsCountET;
    @BindView(R.id.mattressStateSpinner)
    Spinner mattressStateSpinner;
    @BindView(R.id.mattressCountET)
    EditText mattressCountET;
    @BindView(R.id.pillowsStateSpinner)
    Spinner pillowsStateSpinner;
    @BindView(R.id.pillowsCountET)
    EditText pillowsCountET;
    @BindView(R.id.blanketsStateSpinner)
    Spinner blanketsStateSpinner;
    @BindView(R.id.blanketsCountET)
    EditText blanketsCountET;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.failureFixingCheckBox)
    CheckBox failureFixingCheckBox;
    HousingInterface housingInterface;
    FormsPresenter presenter;
    NATIONALITY[] nationalities;
    QuestionnairesInterface questionnairesInterface;
    QuestionnairesPresenter presenter2;
    Detection_of_housing01_03 obj;
    HOUSING_BUILDING_DATA housing_building;
    int tabs;
    NEW_TASKS task;
    Category category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lodging_detection_01_03_fragment, container, false);
        ButterKnife.bind(this, view);
        housingInterface = this;
        questionnairesInterface = this;
        presenter2 = new QuestionnairesPresenter(questionnairesInterface, new QuestionnairesRepositoryImp());
        presenter = new FormsPresenter(housingInterface);
        presenter.getNationality();
        Gson gson = new Gson();
        String json = getArguments().getString("house");
        if (json!=null && !json.equals("")) {
            housing_building = gson.fromJson(json, HOUSING_BUILDING_DATA.class);
                buildingNoET.setText(housing_building.HOUSING_BUILDING_ID);
                permitNoEditText.setText(housing_building.HOUSING_BUILDING_LICENSE_NO);
        }

        tabs = getArguments().getInt("tabs");
        task = (NEW_TASKS) getArguments().getSerializable("task");
        category = (Category) getArguments().getSerializable("category");

        String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
        String[] houseType = {"مسلح", "قديم", "شعبى"};
        String[] enough = {"كافية", "غير كافية"};
        String[] type = {"معدن", "غير معدن"};
        String[] suitable = {"مناسب", "غير مناسب"};
        String[] available = {"نعم", "لا"};
        String[] state = {"جيدة", "سيئة"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        ArrayAdapter spinnerArrayAdapter2 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, available);
        ArrayAdapter spinnerArrayAdapter3 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, houseType);
        ArrayAdapter spinnerArrayAdapter4 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, enough);
        ArrayAdapter spinnerArrayAdapter5 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, type);
        ArrayAdapter spinnerArrayAdapter6 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, suitable);
        ArrayAdapter spinnerArrayAdapter7 = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, state);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        busAvailabilitySpinner.setAdapter(spinnerArrayAdapter2);
        permitCopySpinner.setAdapter(spinnerArrayAdapter2);
        elevatorFoundSpinner.setAdapter(spinnerArrayAdapter2);
        lightEnoughSpinner.setAdapter(spinnerArrayAdapter2);
        buildingTypeSpinner.setAdapter(spinnerArrayAdapter3);
        naturalLightEnoughSpinner.setAdapter(spinnerArrayAdapter4);
        cleaningToolsFoundSpinner.setAdapter(spinnerArrayAdapter2);
        cleaningToolsEnoughSpinner.setAdapter(spinnerArrayAdapter4);
        bedsTypesSpinner.setAdapter(spinnerArrayAdapter5);
        mattressStateSpinner.setAdapter(spinnerArrayAdapter6);
        pillowsStateSpinner.setAdapter(spinnerArrayAdapter6);
        blanketsStateSpinner.setAdapter(spinnerArrayAdapter6);
        ACStateSpinner.setAdapter(spinnerArrayAdapter7);
        slopeFoundSpinner.setAdapter(spinnerArrayAdapter2);
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
                        buildingLocationEditText.getText().toString().equals("") || buildingNoET.getText().toString().equals("") ||
                        permitNoEditText.getText().toString().equals("") || distanceFromMainStreetET.getText().toString().equals("") ||
                        distanceFromSacredET.getText().toString().equals("") || slopeTypeEditText.getText().toString().equals("") ||
                        floorsRentedET.getText().toString().equals("") || roomsNoET.getText().toString().equals("") ||
                        kitchensNoET.getText().toString().equals("") || toiletsNoET.getText().toString().equals("") ||
                        fansNoET.getText().toString().equals("") || refrigeratorsNoET.getText().toString().equals("") ||
                        fireExtinguishersNoEditText.getText().toString().equals("") || expirationDateEditText.getText().toString().equals("") ||
                        sandPailCountET.getText().toString().equals("") || wastePailCountET.getText().toString().equals("") ||
                        elevatorWidthET.getText().toString().equals("") || bedsCountET.getText().toString().equals("") ||
                        mattressCountET.getText().toString().equals("") || pillowsCountET.getText().toString().equals("") ||
                        blanketsCountET.getText().toString().equals("")) {
                    Toast.makeText(App.getContext(), "برجاء ادخال البيانات كامله", Toast.LENGTH_LONG).show();
                } else {
                    Gson gson = new Gson();
                    String json = getArguments().getString("01_03");
                    obj = gson.fromJson(json, Detection_of_housing01_03.class);
                    obj.Visit_Day = dayEditText.getText().toString();
                    obj.Visit_Date = ArabicNumber(dayNoEditText.getText().toString()) + "/" + ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition() + 1)) + "/" +
                            ArabicNumber(yearEditText.getText().toString());
                    DateFormat format = new SimpleDateFormat("h:mm", Locale.ENGLISH);
                    try {
                        Date date = format.parse(timeEditText.getText().toString());
                        obj.Visit_Time = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    for (NATIONALITY key : nationalities) {
                        if (hajNationalityEditText.getSelectedItem().toString().equals(key.ADESC)) {
                            obj.Pilgrims_Nationality = key.SIKEY;
                            break;
                        }
                    }
                    obj.License_ID = permitNoEditText.getText().toString();
                    obj.Far_From_main_Street = distanceFromMainStreetET.getText().toString();
                    obj.Far_From_Elharam = distanceFromSacredET.getText().toString();
                    obj.Bus_Reached_ToPlace = busAvailabilitySpinner.getSelectedItem().toString().equals("نعم");
                    obj.Slope_Found = slopeFoundSpinner.getSelectedItem().toString().equals("نعم");
                    obj.Slope_Type = slopeTypeEditText.getText().toString();
                    if (buildingTypeSpinner.getSelectedItem().toString().equals("مسلح")) {
                        obj.House_Kind = 0;
                    } else if (buildingTypeSpinner.getSelectedItem().toString().equals("قديم")) {
                        obj.House_Kind = 1;
                    } else {
                        obj.House_Kind = 2;
                    }
                    obj.House_LicesnsePhoto = permitCopySpinner.getSelectedItem().toString().equals("نعم");
                    obj.House_Remarks = lightDamageToiletET.getText().toString();
                    obj.Rented_Floors_Num = floorsRentedET.getText().toString();
                    obj.Rented_Rooms_Num = roomsNoET.getText().toString();
                    obj.Kitchens_Rented_Num = kitchensNoET.getText().toString();
                    obj.Rented_Bathroom_Num = toiletsNoET.getText().toString();
                    obj.Rentedd_Fan_Num = fansNoET.getText().toString();
                    obj.Rented_Kolldair_Num = refrigeratorsNoET.getText().toString();
                    obj.AirConditioning_State = ACStateSpinner.getSelectedItem().toString();
                    obj.Fire_Extinguisher_Num = fireExtinguishersNoEditText.getText().toString();
                    obj.Expire_Date = expirationDateEditText.getText().toString();
                    obj.Strain_Of_Sand_Num = sandPailCountET.getText().toString();
                    obj.Strain_Bins_Num = wastePailCountET.getText().toString();
                    obj.Lifter_Found = elevatorFoundSpinner.getSelectedItem().toString().equals("نعم");
                    obj.Lifter_Capacity = elevatorWidthET.getText().toString();
                    obj.Lightning_Enough = lightEnoughSpinner.getSelectedItem().toString().equals("كافية");
                    obj.Cleaning_Tools_Found = cleaningToolsFoundSpinner.getSelectedItem().toString().equals("نعم");
                    obj.Cleaning_Tools_Enough = cleaningToolsEnoughSpinner.getSelectedItem().toString().equals("كافية");
                    obj.Beds_num = bedsCountET.getText().toString();
                    obj.Bed_Enough = bedsTypesSpinner.getSelectedItem().toString().equals("معدن");
                    obj.Mattress_Enough = mattressStateSpinner.getSelectedItem().toString().equals("مناسب");
                    obj.Mattress_Num = mattressCountET.getText().toString();
                    obj.Cushions_Num = pillowsCountET.getText().toString();
                    obj.Cushions_Enough = pillowsStateSpinner.getSelectedItem().toString().equals("مناسب");
                    obj.Blanket_Enough = blanketsStateSpinner.getSelectedItem().toString().equals("مناسب");
                    obj.Blanket_Num = blanketsCountET.getText().toString();
                    obj.Addation_Num = "01-03";
                    obj.Report_Active = true;
                    if (housing_building != null) {
                        obj.House_ID = housing_building.HOUSING_SYSKEY;
                    }

                    json = gson.toJson(obj);
                    presenter2.questionnaires(json, "01-03", "Add");
                }
            }
        });
        return view;
    }

    @Override
    public void houses(HOUSING_BUILDING_DATA housing_building) {

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
                hajNationalityEditText.setAdapter(spinnerArrayAdapter1);
            }
        }
    }

    @Override
    public void SaveObject(String save) {
        Toast.makeText(App.getContext(), "تم اضافة الاستبيان بنجاح", Toast.LENGTH_LONG).show();
        if (failureFixingCheckBox.isChecked()) {
            Intent intent = new Intent(App.getContext(), FailureFixingReportActivity.class);
            intent.putExtra("task", task);
            intent.putExtra("category", category);
            intent.putExtra("issueId", obj.Issue_ID);
            intent.putExtra("tabs", tabs);
            App.getContext().startActivity(intent);
            ((Activity) App.getContext()).finish();
        } else {
            Intent i = new Intent(App.getContext(), TaskActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("Task", task);
            b.putSerializable("Category", category);
            if(tabs == 4) b.putInt("Tab", 3);
            else b.putInt("Tab",2);
            i.putExtras(b);
            App.getContext().startActivity(i);
            ((Activity) App.getContext()).finish();
        }
    }

    @Override
    public void NotSaved(String error) {
        Toast.makeText(App.getContext(), "لم يتم أضافة الاستبيان من فضلك افحص اتصال بالأنترنت وحاول مرة آخرى", Toast.LENGTH_LONG).show();
    }
}
