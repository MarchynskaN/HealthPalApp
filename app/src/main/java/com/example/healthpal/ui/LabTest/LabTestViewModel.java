package com.example.healthpal.ui.LabTest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class LabTestViewModel extends ViewModel {

    private final MutableLiveData<List<LabTest>> mTests;

    final String[][] packages =
            {
                    {"Package 1 : Full Body Checkup", "","","","999"},
                    {"Package 2 : Blood Glucose Fasting", "","","","299"},
                    {"Package 3 : COVID-19 Antibody - IgG", "","","","899"},
                    {"Package 4 : Thyroid Check", "","","","499"},
                    {"Package 5 : Immunity Check", "","","","699"}
            };
    final String[] package_details={
            "Blood Glucose Fasting\n"+
                    "Complete Hemogram\n"+
                    "HbA1c\n"+
                    "Iron Studies\n"+
                    "Kidney Function Test\n"+
                    "LDH Lactate Dehydrogenase, Serum\n"+
                    "Lipid Profile\n"+
                    "Liver Function Test",
            "Blood Glucose Fasting",
            "COVID-19 Antibody - IgG",
            "Thyroid Profile-Total(T3, T4 & TSH Ultra-sensitive)",
            "Complete Hemogram\n"+
                    "CRP(C Reactive Protein) Quantitative, Serum\n"+
                    "Iron Studies\n"+
                    "Kidney Function Test\n"+
                    "Vitamin D Total-25 Hydroxy\n"+
                    "Liver Function Test\n"+
                    "Lipid Profile"
    };

    public LabTestViewModel() {
        mTests = new MutableLiveData<List<LabTest>>();
        List<LabTest> tests = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            String title = packages[i][0];
            String price = packages[i][packages[i].length - 1];
            String details = package_details[i];
            tests.add(new LabTest(title, details, price));
        }
        mTests.setValue(tests);
    }

    public LiveData<List<LabTest>> getTests() {
        return mTests;
    }
}