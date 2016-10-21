package com.example.parasdhanta.stressmanagement.managers;

/**
 * Created by Paras Dhanta on 10/19/2016.
 */

public interface ActivityAddPopInterface {

    public void addActivity(BaseActivity activity);

    public void finishCurrentActivity(BaseActivity activity);

    public void destroyApplicationForcefully();


}
