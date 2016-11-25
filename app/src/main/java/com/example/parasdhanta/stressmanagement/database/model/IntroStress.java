package com.example.parasdhanta.stressmanagement.database.model;

import io.realm.RealmObject;

/**
 * Created by Paras Dhanta on 11/24/2016.
 */

public class IntroStress extends RealmObject {

    String whatIsStress;

    String causesOfStress;

    String symptomsOfStress;

    private long id;

    public String getSymptomsOfStress() {
        return symptomsOfStress;
    }

    public void setSymptomsOfStress(String symptomsOfStress) {
        this.symptomsOfStress = symptomsOfStress;
    }

    public String getWhatIsStress() {
        return whatIsStress;
    }

    public void setWhatIsStress(String whatIsStress) {
        this.whatIsStress = whatIsStress;
    }

    public String getCausesOfStress() {
        return causesOfStress;
    }

    public void setCausesOfStress(String causesOfStress) {
        this.causesOfStress = causesOfStress;
    }
}
