package com.firrael.psychology.presenter;

import android.os.Bundle;

import com.firrael.psychology.App;
import com.firrael.psychology.RConnectorService;
import com.firrael.psychology.view.CirclesTestFragment;

import icepick.State;

/**
 * Created by Railag on 16.03.2017.
 */

public class CirclesTestPresenter extends BasePresenter<CirclesTestFragment> {

    @State
    String name;

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

    public void save(String name) {
        this.name = name;
        //    User.get(App.getMainActivity()).setName(name);
        //    start(REQUEST_LOGIN);
    }
}