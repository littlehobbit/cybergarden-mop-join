package com.example.appmobile.net.entries;

public class RegistrationParamsUserSettings {
    String username;
    String password;
    String email;
    String phone;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public RegistrationParamsUserSettings(String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
