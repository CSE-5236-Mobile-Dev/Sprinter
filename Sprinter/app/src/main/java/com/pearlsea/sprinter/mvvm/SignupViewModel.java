package com.pearlsea.sprinter.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignupViewModel extends ViewModel {
    private String status = "";
    private Boolean isError = false;
    private MutableLiveData<String> liveStatus = new MutableLiveData<>(status);
    private MutableLiveData<Boolean> liveIsError = new MutableLiveData<>(isError);

    public void setStatus(String newStatus, boolean isError) {
        liveStatus.postValue(newStatus);
        liveIsError.postValue(isError);
    }

    public LiveData<String> getStatus() {
        return liveStatus;
    }

    public LiveData<Boolean> getIsError() {
        return liveIsError;
    }
}
