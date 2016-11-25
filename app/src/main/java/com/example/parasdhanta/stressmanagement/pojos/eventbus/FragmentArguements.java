package com.example.parasdhanta.stressmanagement.pojos.eventbus;

/**
 * Created by Paras Dhanta on 11/8/2016.
 */

public class FragmentArguements {

    String title;
    String content;

    public FragmentArguements(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
