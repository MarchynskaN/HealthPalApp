package com.example.healthpal.ui.MyAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyAccountViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyAccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}