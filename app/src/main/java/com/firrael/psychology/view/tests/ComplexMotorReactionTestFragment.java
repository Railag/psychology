package com.firrael.psychology.view.tests;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firrael.psychology.App;
import com.firrael.psychology.R;
import com.firrael.psychology.model.Difficulty;
import com.firrael.psychology.model.MotorCircle;
import com.firrael.psychology.model.Result;
import com.firrael.psychology.presenter.ComplexMotorReactionTestPresenter;
import com.firrael.psychology.view.base.BaseFragment;
import com.firrael.psychology.view.results.ComplexMotorReactionResultsFragment;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 19.04.2017.
 */

@RequiresPresenter(ComplexMotorReactionTestPresenter.class)
public class ComplexMotorReactionTestFragment extends BaseFragment<ComplexMotorReactionTestPresenter> {

    private final static int MAX_COUNT = 20;

    private MotorCircle current = MotorCircle.GREY;

    @BindView(R.id.reactionImage)
    ImageView image;

    private Handler handler;

    boolean active;

    private int currentStep = 0;

    int wins = 0;
    int fails = 0;
    int misses = 0;

    public static ComplexMotorReactionTestFragment newInstance() {

        Bundle args = new Bundle();

        ComplexMotorReactionTestFragment fragment = new ComplexMotorReactionTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.complexMotorReactionTestTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_test_complex_motor_reaction;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();

        next();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void next() {
        int startTime = 1200;

        handler.postDelayed(() -> {
            if (active) {
                misses++;
                active = false;
            }

            currentStep++;
            action();

        }, startTime);
    }

    @OnClick(R.id.buttonGreen)
    public void greenClick() {
        click(false);
    }

    @OnClick(R.id.buttonRed)
    public void redClick() {
        click(true);
    }

    public void click(boolean isRed) {

        if (active) {
            switch (current) {
                case GREEN:
                    if (isRed)
                        fails++;
                    else
                        wins++;
                    break;
                case RED:
                    if (isRed)
                        wins++;
                    else
                        fails++;
                    break;

                case GREY:
                case YELLOW:
                default:
                    fails++;
                    return;
            }

            active = false;
        } else {
            fails++;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void toNextTest() {
        startLoading();
        getPresenter().save(wins, fails, misses);
    }

    public void onSuccess(Result result) {
        stopLoading();

        if (result == null) {
            onError(new IllegalArgumentException());
            return;
        }
        if (result.invalid()) {
            toast(result.error);
            return;
        }
        toast("success");

        Bundle args = new Bundle();
        args.putInt(ComplexMotorReactionResultsFragment.WINS, wins);
        args.putInt(ComplexMotorReactionResultsFragment.FAILS, fails);
        getMainActivity().toComplexMotorReactionResults(args);
    }

    public void onError(Throwable throwable) {
        stopLoading();
        throwable.printStackTrace();
    }


    private void action() {
        if (getActivity() == null) {
            return;
        }

        MotorCircle previous = current;
        while (previous.equals(current)) {
            current = MotorCircle.random();
        }

        Resources resources = getResources();

        image.setImageDrawable(resources.getDrawable(current.getDrawableId()));

        switch (current) {
            case GREEN:
            case RED:
                active = true;
                break;

            case GREY:
            case YELLOW:
            default:
                break;
        }

        Difficulty diff = App.diff(getActivity());

        if (currentStep > MAX_COUNT * diff.getLevel()) {
            toNextTest();
        } else {
            next();
        }
    }
}
