package com.example.appmobile.net.entries;

import java.util.ArrayList;

public class TestDataResults {
    public Integer id;
    public String question;
    public ArrayList<Answer> answers;

    public TestDataResults(Integer id,
                           String question,
                           ArrayList<Answer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public class Answer {
        public String answer;
    }
}
