package com.example.appmobile.net.entries;

public class QaListResults {

    String  question;
    String shortAnswer;
    String fullAnswer;
    boolean isDescriptionShown = false;

    public String getQuestion() {
        return question;
    }

    public boolean getIsDescriptionShown() {
        return isDescriptionShown;
    }

    public void swapIsDescriptionShown() {
        isDescriptionShown = !isDescriptionShown;
    }


    public String getShortAnswer() {
        return shortAnswer;
    }

    public String getFullAnswer() {
        return fullAnswer;
    }
}
