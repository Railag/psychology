package com.firrael.psychology.view;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firrael.psychology.R;
import com.firrael.psychology.Utils;
import com.firrael.psychology.model.Answer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Railag on 30.03.2017.
 */

public class StressResistanceTestFragment extends SimpleFragment {

    @BindView(R.id.circle1)
    ImageView circle1;
    @BindView(R.id.circle2)
    ImageView circle2;
    @BindView(R.id.circle3)
    ImageView circle3;
    @BindView(R.id.circle4)
    ImageView circle4;
    @BindView(R.id.circle5)
    ImageView circle5;
    @BindView(R.id.circle6)
    ImageView circle6;
    @BindView(R.id.circle7)
    ImageView circle7;
    @BindView(R.id.circle8)
    ImageView circle8;
    @BindView(R.id.circle9)
    ImageView circle9;
    @BindView(R.id.circle10)
    ImageView circle10;
    @BindView(R.id.circle11)
    ImageView circle11;
    @BindView(R.id.circle12)
    ImageView circle12;
    @BindView(R.id.circle13)
    ImageView circle13;
    @BindView(R.id.circle14)
    ImageView circle14;
    @BindView(R.id.circle15)
    ImageView circle15;
    @BindView(R.id.circle16)
    ImageView circle16;
    @BindView(R.id.circle17)
    ImageView circle17;
    @BindView(R.id.circle18)
    ImageView circle18;
    @BindView(R.id.circle19)
    ImageView circle19;
    @BindView(R.id.circle20)
    ImageView circle20;

    ImageView[] points;

    int currentPoint = 0;

    Random random = new Random();

    private final static int POINTS_COUNT = 20;
    private final static int MAX_POINT = 19;

    private final static int USUAL_TIME = 3000;
    private final static int ACTION_TIME = 1500;

    private final static int MAX_VALUE = 12;


    private Handler handler;

    private boolean action = false;

    private long time;
    private ArrayList<Answer> answers = new ArrayList<>();

    private int current;

    public static StressResistanceTestFragment newInstance() {

        Bundle args = new Bundle();

        StressResistanceTestFragment fragment = new StressResistanceTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.stressResistanceTestTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_test_stress_resistance;
    }

    @Override
    protected void initView(View v) {
        handler = new Handler();

        points = new ImageView[]{circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10,
                circle11, circle12, circle13, circle14, circle15, circle16, circle17, circle18, circle19, circle20};

        next();
    }

    private boolean checkAction() {
        int randomNumber = random.nextInt(4);

        return randomNumber == 1; // 25% chance
    }

    private void next() {
        int startTime = 1000;

        final boolean isAction = checkAction();
        if (isAction) {
            //        startTime = ACTION_TIME;
            if (currentPoint == MAX_POINT) {
                currentPoint = 0;
            } else {
                currentPoint++;
            }
        } else {
            //        startTime = USUAL_TIME;
        }

        handler.postDelayed(() -> {
            if (isAction) {
                if (action) { // missed event
                    double result = Utils.calcTime(time);
                    Answer answer = new Answer();
                    answer.setTime(result);

                    answer.setErrorValue(1); // error

                    answer.setNumber(answers.size());

                    answers.add(answer);
                }

                action = true;
                time = System.nanoTime();
            }

            nextPoint();

        }, startTime);
    }

    private void nextPoint() {
        Resources res = getResources();
        points[currentPoint].setImageDrawable(res.getDrawable(R.drawable.circle_stress_green));
        Drawable greyDrawable = res.getDrawable(R.drawable.circle_stress);

        for (int i = 0; i < points.length; i++) {
            if (i == currentPoint) {
                continue;
            }

            points[i].setImageDrawable(greyDrawable);
        }

        if (currentPoint == MAX_POINT) {
            currentPoint = 0;
        } else {
            currentPoint++;
        }

        next();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @OnClick(R.id.stressButton)
    void click() {
        if (action) {
            action = false;

            double result = Utils.calcTime(time);

            Answer answer = new Answer();
            answer.setTime(result);

            answer.setErrorValue(0);

            answer.setNumber(answers.size());

            answers.add(answer);

            String diffInSeconds = new DecimalFormat("#.##").format(result);
            String res = diffInSeconds + " секунд";
            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();

            current++;

            if (current >= MAX_VALUE) {
                toNextTest();
            }
        }
    }

    private void toNextTest() {
        Bundle args = new Bundle();
        args.putParcelableArrayList(StressResistanceResultsFragment.RESULTS, answers);
        getMainActivity().toStressResistanceResults(args);
    }
}
