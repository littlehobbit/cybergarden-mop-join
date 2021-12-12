package com.example.appmobile.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class RegistrationViewModel extends ViewModel {

    public final MutableLiveData<String> surname = new MutableLiveData<>();
    public final MutableLiveData<String> name = new MutableLiveData<>();
    public final MutableLiveData<String> middleName = new MutableLiveData<>();
    public final MutableLiveData<String> birthday = new MutableLiveData<>();
    public final MutableLiveData<Integer> role = new MutableLiveData<>(1);

    public final MutableLiveData<String> email = new MutableLiveData<>();
    public final MutableLiveData<String> password = new MutableLiveData<>();

    public void setSurname(String surname) {
        this.surname.postValue(surname);
    }

    public void setName(String name) {
        this.name.postValue(name);
    }

    public void setMiddleName(String middleName) {
        this.middleName.postValue(middleName);
    }

    public static RegistrationViewModel create(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(RegistrationViewModel.class);
    }

    public void setRole(Integer value) {
        this.role.setValue(value);
    }

   public void setEmail(String value) {
        this.email.setValue(value);
   }

    public void setPassword(String value) {
        this.password.setValue(value);
    }

}
