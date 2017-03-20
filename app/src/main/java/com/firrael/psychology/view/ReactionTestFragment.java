package com.firrael.psychology.view;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firrael.psychology.R;
import com.firrael.psychology.Utils;
import com.firrael.psychology.presenter.ReactionWhiteTestPresenter;

import java.text.DecimalFormat;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 07.11.2016.
 */
@RequiresPresenter(ReactionWhiteTestPresenter.class)
public class ReactionTestFragment extends BaseFragment<ReactionWhiteTestPresenter> {


    private final static int MAX_COUNT = 10;

    @BindView(R.id.whiteTestText)
    TextView text;

    @BindView(R.id.whiteTestBackground)
    View background;

    private long time;

    private Handler handler;

    private double results[] = new double[MAX_COUNT];
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
        int startTime = new Random().nextInt(5000);
        if (startTime < 2000) {
            startTime = 3000;
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
            results[current] = result;
            String diffInSeconds = new DecimalFormat("#.##").format(result);
            String res = diffInSeconds + " секунд";
            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();

            current++;

            if (current < MAX_COUNT) {
                text.setVisibility(View.VISIBLE);
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
        // TODO

        Bundle args = new Bundle();
        args.putDoubleArray(ReactionResultsFragment.RESULTS, results);
        getMainActivity().toReactionResults(args);
    }

    private void action() {
        text.setVisibility(View.GONE);
        background.setBackgroundColor(getResources().getColor(android.R.color.white));
        time = System.nanoTime();
    }
}
