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
import com.firrael.psychology.Utils;
import com.firrael.psychology.model.Answer;
import com.firrael.psychology.presenter.FirstTestPresenter;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 06.03.2017.
 */

@RequiresPresenter(FirstTestPresenter.class)
public class AttentionStabilityTestFragment extends BaseFragment<FirstTestPresenter> {

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

    private long time;

    private ArrayList<Answer> answers = new ArrayList<>();

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

        for (int i = 0; i < 100; i++) {
            if (i < 10) { // first 10 numbers
                progressTime += randomTime.nextInt(800);
                handler.postDelayed(() -> {
                    int num = generateRandomNumber();
                    number.setText(String.valueOf(num));
                }, progressTime);
            } else if (i % 10 == 0) { // first square
                progressTime += 800;
                handler.postDelayed(this::showRedBackground, progressTime);
            } else if (i == 99) {
                progressTime += 250;
                handler.postDelayed(this::toNextTest, progressTime);
            } else if (i % 10 == 1) { // number between
                progressTime += 700;
                handler.postDelayed(() -> {
                    currentNum = generateRandomNumber(); // number before second red square
                    number.setText(String.valueOf(currentNum));
                    showNumber();
                    time = System.nanoTime();
                    active = true;
                }, progressTime);
            } else if (i % 10 == 2) { // second square
                progressTime += 300;
                handler.postDelayed(this::showRedBackground, progressTime);
            } else if (i % 10 == 3) { // hide second square
                progressTime += 700;
                handler.postDelayed(this::showNumber, progressTime);
            } else if (i % 10 == 4) {
                progressTime += 50;
                handler.postDelayed(() -> {
                    int num = generateRandomNumber();
                    number.setText(String.valueOf(num));
                }, progressTime);
            } else {
                progressTime += randomTime.nextInt(800);
                handler.postDelayed(() -> {
                    int num = generateRandomNumber();
                    number.setText(String.valueOf(num));
                    active = false;
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

        double resultTime = Utils.calcTime(time);
        Answer answer = new Answer();
        answer.setTime(resultTime);

        boolean evenNumber = currentNum % 2 == 0; // четное

        if (right) {
            if (evenNumber) {
                fails++;
                answer.setErrorValue(1);
            } else {
                wins++;
                answer.setErrorValue(0);
            }
        } else {
            if (!evenNumber) {
                fails++;
                answer.setErrorValue(1);
            } else {
                wins++;
                answer.setErrorValue(0);
            }
        }

        answer.setNumber(answers.size());
        answers.add(answer);

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
        Bundle args = new Bundle();
        args.putParcelableArrayList(AttentionStabilityResultsFragment.RESULTS, answers);
        getMainActivity().toAttentionStabilityResults(args);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}