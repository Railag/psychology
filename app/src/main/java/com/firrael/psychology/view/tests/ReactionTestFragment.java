package com.firrael.psychology.view.tests;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firrael.psychology.App;
import com.firrael.psychology.R;
import com.firrael.psychology.Utils;
import com.firrael.psychology.model.Difficulty;
import com.firrael.psychology.model.Result;
import com.firrael.psychology.presenter.ReactionTestPresenter;
import com.firrael.psychology.view.base.BaseFragment;
import com.firrael.psychology.view.results.ReactionResultsFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 07.11.2016.
 */
@RequiresPresenter(ReactionTestPresenter.class)
public class ReactionTestFragment extends BaseFragment<ReactionTestPresenter> {


    private final static int MAX_COUNT = 10;

    @BindView(R.id.whiteTestText)
    TextView text;

    @BindView(R.id.current)
    TextView currentView;

    @BindView(R.id.reactNumberLayout)
    LinearLayout reactNumberLayout;

    @BindView(R.id.whiteTestBackground)
    View background;

    private long time;

    private Handler handler;

    private ArrayList<Double> results = new ArrayList<>();
    private int current = 0;

    public static ReactionTestFragment newInstance() {

        Bundle args = new Bundle();

        ReactionTestFragment fragment = new ReactionTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.reactionTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_test_reaction_white;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();

        next();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void next() {
        Difficulty diff = App.diff(getActivity());
        int startTime = new Random().nextInt(2000 * diff.getLevel());
        if (startTime < 2000) {
            startTime = 2500;
        }

        handler.postDelayed(() -> {
            stopLoading();
            action();

        }, startTime);
    }

    @OnClick(R.id.whiteTestBackground)
    public void click() {

        if (text.getVisibility() == View.GONE) {
            double result = Utils.calcTime(time);
            results.add(result);

            String diffInSeconds = new DecimalFormat("#.##").format(result);
            String res = diffInSeconds + " секунд";
            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();

            current++;

            if (current < MAX_COUNT) {
                text.setVisibility(View.VISIBLE);
                reactNumberLayout.setVisibility(View.VISIBLE);

                currentView.setText(String.valueOf(MAX_COUNT - current));
                background.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                time = System.nanoTime();
                next();
            } else {
                toNextTest();
            }
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

        getPresenter().save(results);
    }

    private void action() {
        text.setVisibility(View.GONE);
        reactNumberLayout.setVisibility(View.GONE);
        background.setBackgroundColor(getResources().getColor(android.R.color.white));
        time = System.nanoTime();
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
        args.putSerializable(ReactionResultsFragment.RESULTS, results);
        getMainActivity().toReactionResults(args);
    }

    public void onError(Throwable throwable) {
        stopLoading();
        throwable.printStackTrace();
    }
}
