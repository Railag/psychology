package com.firrael.psychology.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firrael.psychology.R;
import com.firrael.psychology.presenter.FirstTestPresenter;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 06.03.2017.
 */

@RequiresPresenter(FirstTestPresenter.class)
public class AttentionStabilityTestFragment extends BaseFragment<FirstTestPresenter> {

    private final static double MILLIS = 1000000000;

    private final static int MAX_NUMBER = 10;

    private Handler handler;

    private int wins;
    private int fails;

    @BindView(R.id.number)
    TextView number;

    @BindView(R.id.background)
    View background;

    private Random random = new Random();

    private int currentNum = -1;

    private int progressTime = 0;

    private boolean active;

    public static AttentionStabilityTestFragment newInstance() {

        Bundle args = new Bundle();

        AttentionStabilityTestFragment fragment = new AttentionStabilityTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.attentionStabilityTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_test_first;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();

        Random randomTime = new Random();

        for (int i = 1; i < 100; i++) {
            if (i % 10 == 0) { // first square
                progressTime += randomTime.nextInt(300);
                handler.postDelayed(this::showRedBackground, progressTime);
            } else if (i % 11 == 0) { // number between
                progressTime += randomTime.nextInt(1000);
                handler.postDelayed(this::showNumber, progressTime);
            } else if (i % 12 == 0) { // second square
                progressTime += randomTime.nextInt(200);
                handler.postDelayed(() -> {
                    currentNum = Integer.parseInt(number.getText().toString()); // number before second red square
                    active = true;
                    showRedBackground();
                }, progressTime);
            } else if (i % 13 == 0) { // hide second square
                progressTime += randomTime.nextInt(1000);
                handler.postDelayed(this::showNumber, progressTime);
            } else {
                progressTime += 1000;
                handler.postDelayed(() -> {
                    int num = generateRandomNumber();
                    number.setText(String.valueOf(num));
                }, progressTime);
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.leftButton) // четное
    public void leftClick() {
        click(false);
    }

    @OnClick(R.id.rightButton) // нечетное
    public void rightClick() {
        click(true);
    }

    public void click(boolean right) {

        if (!active)
            return;

        boolean evenNumber = currentNum % 2 == 0; // четное

        if (right) {
            if (evenNumber)
                fails++;
            else
                wins++;
        } else {
            if (!evenNumber)
                fails++;
            else
                wins++;
        }

        active = false;

        Toast.makeText(getActivity(), "Wins = " + wins + ", Fails = " + fails, Toast.LENGTH_SHORT).show();

        /*if (number.getVisibility() == View.GONE) {
            long currTime = System.nanoTime();
            long diff = currTime - time;
            String diffInSeconds = new DecimalFormat("#.##").format(diff / MILLIS);
            String result = diffInSeconds + " секунд";
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            toNextTest();
        }*/
    }

    private int generateRandomNumber() {
        return random.nextInt(MAX_NUMBER);
    }

    private void showRedBackground() {
        background.setVisibility(View.VISIBLE);
    }

    private void showNumber() {
        background.setVisibility(View.GONE);
    }

    private void toNextTest() {
        // TODO
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void action() {
        number.setVisibility(View.GONE);
        background.setBackgroundColor(getResources().getColor(android.R.color.white));
        //    time = System.nanoTime();
    }
}