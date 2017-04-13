package com.developer.sparsh.baseapplication.Classes;

/**
 * Created by utkarshnath on 08/04/17.
 */

public class AdminContact {
    private String name;
    private String number;
    private String email;
    private boolean isSelected;

    public AdminContact() {
        this.name = null;
        this.number = null;
        this.email = null;
        isSelected = false;
    }

    public AdminContact(String name, String number, String email){
        this.name = name;
        this.number = number;
        this.email = email;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
