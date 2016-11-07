package com.firrael.psychology.presenter;

import android.os.Bundle;

import com.firrael.psychology.App;
import com.firrael.psychology.RConnectorService;
import com.firrael.psychology.model.User;
import com.firrael.psychology.view.AgeFragment;
import com.firrael.psychology.view.TimeFragment;

import icepick.State;

/**
 * Created by Railag on 07.11.2016.
 */

public class TimePresenter extends BasePresenter<TimeFragment> {

    @State
    int time;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        RConnectorService service = App.restService();

        /*restartableLatestCache(REQUEST_LOGIN,
                () -> service.login(login, password)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()),
                LoginFragment::onSuccess,
                LoginFragment::onError);*/
    }

    public void save(String time) {
        try {
            int timeNumber = Integer.valueOf(time);
            this.time = timeNumber;
            User.get(App.getMainActivity()).setAge(timeNumber);
            App.getMainActivity().toUserLandingScreen();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //    start(REQUEST_LOGIN);
    }
}
