package com.firrael.psychology.presenter;

import android.os.Bundle;

import com.firrael.psychology.App;
import com.firrael.psychology.RConnectorService;
import com.firrael.psychology.model.User;
import com.firrael.psychology.view.tests.ReactionTestFragment;

import java.util.ArrayList;

import icepick.State;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.firrael.psychology.Requests.REQUEST_RESULTS_REACTION;

/**
 * Created by Railag on 07.11.2016.
 */

public class ReactionTestPresenter extends BasePresenter<ReactionTestFragment> {

    @State
    long userId;

    @State
    ArrayList<Double> times;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        RConnectorService service = App.restService();

        restartableLatestCache(REQUEST_RESULTS_REACTION,
                () -> service.sendReactionResults(userId, times)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()),
                ReactionTestFragment::onSuccess,
                ReactionTestFragment::onError);
    }

    public void save(ArrayList<Double> times) {
        this.userId = User.get(App.getMainActivity()).getId();
        this.times = times;

        start(REQUEST_RESULTS_REACTION);
    }
}
