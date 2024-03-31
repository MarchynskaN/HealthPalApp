package com.example.healthpal.ui.FindDoctor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindDoctorViewModel extends ViewModel {

    private final MutableLiveData<List<Doctor>> mDoctors = new MutableLiveData<>();
    private HashMap<String, String[][]> doctorDetailsMap = new HashMap<>();

    private String[][] doctor_details1 =
            {
                    {"Doctor Name : Nadiia Family Physician", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "100"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "200"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "300"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "400"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "500"}
            };
    private String[][] doctor_details2 =
            {
                    {"Doctor Name : Nadiis Dietician", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "600"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "700"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "800"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "900"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "1000"}
            };
    private String[][] doctor_details3 =
            {
                    {"Doctor Name : Nadiia Dentist", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "1100"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "1200"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "1300"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "1400"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "1500"}
            };
    private String[][] doctor_details4 =
            {
                    {"Doctor Name : Nadiia Surgeon", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "1600"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "1700"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "1800"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "1900"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "2000"}
            };
    private String[][] doctor_details5 =
            {
                    {"Doctor Name : Nadiia Cardiologist", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "2100"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "2200"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "2300"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "2400"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "2500"}
            };

    private String[][] doctor_details6 =
            {
                    {"Doctor Name : Nadiia Other", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "2100"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "2200"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "2300"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "2400"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "2500"}
            };

    public FindDoctorViewModel() {
        doctorDetailsMap.put("Family Physicians", doctor_details1);
        doctorDetailsMap.put("Dietician", doctor_details2);
        doctorDetailsMap.put("Dentist", doctor_details3);
        doctorDetailsMap.put("Surgeon", doctor_details4);
        doctorDetailsMap.put("Cardiologist", doctor_details5);
        doctorDetailsMap.put("Other", doctor_details6);
    }

    public void setSelectedType(String type) {
        String[][] details = doctorDetailsMap.getOrDefault(type, new String[0][0]);
        List<Doctor> doctors = convertToDoctorList(details);
        mDoctors.setValue(doctors);
    }
    private List<Doctor> convertToDoctorList(String[][] doctorDetails) {
        List<Doctor> doctors = new ArrayList<>();
        for (String[] details : doctorDetails) {
            Doctor doctor = new Doctor(details[0], details[1], details[2], details[3], details[4]);
            doctors.add(doctor);
        }
        return doctors;
    }

    public LiveData<List<Doctor>> getDoctors() {
        return mDoctors;
    }
}