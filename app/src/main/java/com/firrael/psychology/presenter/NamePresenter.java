package com.firrael.psychology.presenter;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;

import com.firrael.psychology.App;
import com.firrael.psychology.RConnectorService;
import com.firrael.psychology.model.User;
import com.firrael.psychology.view.NameFragment;

import icepick.State;

/**
 * Created by Railag on 07.11.2016.
 */

public class NamePresenter extends BasePresenter<NameFragment> {

    @State
    String name;

    @State
    String password;

    public void save(String name, String password) {
        this.name = name;
        User.get(App.getMainActivity()).setLogin(name);
        User.get(App.getMainActivity()).setPassword(password);
    }
}