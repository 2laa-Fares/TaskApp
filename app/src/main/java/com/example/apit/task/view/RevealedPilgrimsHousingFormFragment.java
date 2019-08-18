package com.example.apit.task.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.Detection_of_housing01_03;
import com.example.apit.task.model.HOUSING_BUILDING_DATA;
import com.example.apit.task.model.NATIONALITY;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.FormsPresenter;
import com.example.apit.task.utils.PrefUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.apit.task.utils.EnglishNumberToArabic.ArabicNumber;

public class RevealedPilgrimsHousingFormFragment extends Fragment implements HousingInterface{

    @BindView(R.id.officeNoEditText)
    EditText officeNoEditText;
    @BindView(R.id.organizerNoEditText)
    EditText organizerNoEditText;
    @BindView(R.id.houseNoEditText)
    EditText houseNoEditText;
    @BindView(R.id.tenantOrOwnerEditText)
    EditText tenantOrOwnerEditText;
    @BindView(R.id.BuildingNameEditText)
    EditText BuildingNameEditText;
    @BindView(R.id.serviceProviderEditText)
    EditText serviceProviderEditText;
    @BindView(R.id.telephoneEditText)
    EditText telephoneEditText;
    @BindView(R.id.faxEditText)
    EditText faxEditText;
    @BindView(R.id.phoneNoEditText)
    EditText phoneNoEditText;
    @BindView(R.id.permitNoEditText)
    EditText permitNoEditText;
    @BindView(R.id.dayNoEditText)
    EditText dayNoEditText;
    @BindView(R.id.monthSpinner)
    Spinner monthSpinner;
    @BindView(R.id.yearEditText)
    EditText yearEditText;
    @BindView(R.id.ownerNameEditText)
    EditText ownerNameEditText;
    @BindView(R.id.telephoneNoEditText)
    EditText telephoneNoEditText;
    @BindView(R.id.mobileNoEditText)
    EditText mobileNoEditText;
    @BindView(R.id.housePhoneNoEditText)
    EditText housePhoneNoEditText;
    @BindView(R.id.buildingAddressEditText)
    EditText buildingAddressEditText;
    @BindView(R.id.floorsCountEditText)
    EditText floorsCountEditText;
    @BindView(R.id.roomsCountEditText)
    EditText roomsCountEditText;
    @BindView(R.id.kitchenCountEditText)
    EditText kitchenCountEditText;
    @BindView(R.id.hajCountEditText)
    EditText hajCountEditText;
    @BindView(R.id.next)
    Button next;

    HousingInterface housingInterface;
    FormsPresenter presenter;
    NEW_TASKS task;
    Category category;
    int taskID, issueID, tabs;
    HOUSING_BUILDING_DATA housing_building;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revealed_pilgrims_housing_form, container, false);
        ButterKnife.bind(this, view);
        housingInterface = this;
        presenter = new FormsPresenter(housingInterface);
        presenter.getHouses(getArguments().getInt("housingId"));

        task =(NEW_TASKS) getArguments().getSerializable("task");
        taskID = task.TASK_SYSKEY;
        category = (Category) getArguments().getSerializable("category");
        tabs = getArguments().getInt("tabs", 0);
        issueID = getArguments().getInt("issueId");

        organizerNoEditText.setText("بعثة");
        officeNoEditText.setText("32");

        String[] monthS = {"محرم", "صفر", "ربيع الأول", "ربيع الثانى", "جمادي الأولى", "جمادي الآخرة", "رجب", "شعبان",
                "رمضان", "شوال", "ذو القعدة", "ذو الحجة"};
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(App.getContext(), android.R.layout.simple_spinner_item, monthS);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detection_of_housing01_03 obj = new Detection_of_housing01_03();
                obj.Owner_Name = tenantOrOwnerEditText.getText().toString();
                obj.House_Name = BuildingNameEditText.getText().toString();
                obj.Service_provider_Name = serviceProviderEditText.getText().toString();
                obj.Provider_Phone = telephoneEditText.getText().toString();
                obj.Provider_Fax = faxEditText.getText().toString();
                obj.Provider_mobile_Num = phoneNoEditText.getText().toString();
                obj.House_License_ID = permitNoEditText.getText().toString();
                obj.House_License_Date =ArabicNumber(dayNoEditText.getText().toString()) +"/"+ArabicNumber(String.valueOf(monthSpinner.getSelectedItemPosition()+1))+"/"+
                        ArabicNumber(yearEditText.getText().toString());
                obj.Owner_Phone_num = telephoneNoEditText.getText().toString();
                obj.Owner_Mobile_Num =mobileNoEditText.getText().toString();
                obj.Floors_Num = floorsCountEditText.getText().toString();
                obj.Kitchen_Num = kitchenCountEditText.getText().toString();
                obj.Pilgrims_Num = hajCountEditText.getText().toString();
                obj.Owner_Home_Phone = housePhoneNoEditText.getText().toString();
                obj.SYSUSERKEY = Integer.valueOf(PrefUtils.getKeys(App.getContext(), getString(R.string.user_id)));
                obj.Task_ID = taskID;
                obj.Issue_ID = issueID;
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                Bundle bundle = new Bundle();
                bundle.putString("01_03", json);
                if (housing_building!=null){
                    json = gson.toJson(housing_building);
                    bundle.putString("house", json);
                }
                bundle.putSerializable("task", task);
                bundle.putInt("tabs", tabs);
                bundle.putSerializable("category" , category);

                LodgingDetection01_03Fragment fragment = new LodgingDetection01_03Fragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }

    @Override
    public void houses(HOUSING_BUILDING_DATA housing_building) {
        if (housing_building!=null) {
            this.housing_building = housing_building;
            if (housing_building.HOUSING_BUILDING_ID!=null)
            houseNoEditText.setText(housing_building.HOUSING_BUILDING_ID);
            if (housing_building.HOUSING_BUILDING_OWNER_NAME!=null)
                ownerNameEditText.setText(housing_building.HOUSING_BUILDING_OWNER_NAME);
            if (housing_building.HOUSING_BUILDING_ID!=null)
                BuildingNameEditText.setText(housing_building.HOUSING_BUILDING_ID);
            if (housing_building.HOUSING_BUILDING_GARD_NAME!=null)
                serviceProviderEditText.setText(housing_building.HOUSING_BUILDING_GARD_NAME);
            if (housing_building.HOUSING_BUILDING_OWNER_Phone!=null)
                phoneNoEditText.setText(housing_building.HOUSING_BUILDING_OWNER_Phone);
            if (housing_building.HOUSING_BUILDING_LICENSE_NO!=null)
                permitNoEditText.setText(housing_building.HOUSING_BUILDING_LICENSE_NO);
            if (housing_building.HOUSING_BUILDING_OWNER_NAME!=null)
                tenantOrOwnerEditText.setText(housing_building.HOUSING_BUILDING_OWNER_NAME);
            if (housing_building.HOUSING_BUILDING_OWNER_Phone!=null)
                mobileNoEditText.setText(housing_building.HOUSING_BUILDING_OWNER_Phone);
            if (housing_building.HOUSING_BUILDING_FLOOR_NUM!=0)
                floorsCountEditText.setText(String.valueOf(housing_building.HOUSING_BUILDING_FLOOR_NUM));
        }
    }

    @Override
    public void nationalities(NATIONALITY[] nationalities) {

    }
}
