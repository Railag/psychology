package com.firrael.psychology.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firrael.psychology.R;
import com.firrael.psychology.model.Figure;
import com.firrael.psychology.presenter.FiguresTestPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 17.03.2017.
 */

@RequiresPresenter(FiguresTestPresenter.class)
public class AttentionVolumeTestFragment extends BaseFragment<FiguresTestPresenter> {

    private final static int MAX_FIGURES = 100;

    private Handler handler;

    private int wins;
    private int fails;

    @BindView(R.id.firstImage)
    ImageView firstImage;

    @BindView(R.id.secondImage)
    ImageView secondImage;

    Figure figure1;
    Figure figure2;

    private boolean active;

    private int currentFigure = 0;

    private int progressTime = 0;

    public static AttentionVolumeTestFragment newInstance() {

        Bundle args = new Bundle();

        AttentionVolumeTestFragment fragment = new AttentionVolumeTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.attentionVolumeTestTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_test_figures;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View v) {
        figure1 = Figure.random();
        figure2 = Figure.random();
        next();
    }

    private void next() {
        handler.postDelayed(() -> {
            if (getActivity() == null) {
                return;
            }

            if (active && figure1.equals(figure2)) {
                fails++;
            }

            currentFigure++;
            if (currentFigure >= MAX_FIGURES) {
                Toast.makeText(getActivity(), "Wins = " + wins + ", Fails = " + fails, Toast.LENGTH_SHORT).show();
                toNextTest();
                return;
            }

            Figure temp1 = null, temp2 = null;

            while (true) {
                temp1 = Figure.random();
                temp2 = Figure.random();

                if (!figure1.equals(temp1) && !figure2.equals(temp2)) {
                    break;
                }
            }

            figure1 = temp1;
            figure2 = temp2;

            Resources res = getResources();

            firstImage.setImageDrawable(res.getDrawable(figure1.getDrawableId()));
            secondImage.setImageDrawable(res.getDrawable(figure2.getDrawableId()));
            next();
            active = true;
            Log.i("DEBUG", currentFigure + " w:" + wins + " f:" + fails);
        }, 800);
    }

    @OnClick(R.id.button)
    public void click() {

        if (!active) {
            return;
        }

        if (currentFigure > MAX_FIGURES) {
            return;
        }

        if (figure1.equals(figure2)) {
            wins++;
        } else {
            fails++;
        }

        active = false;
    }

    private void toNextTest() {
        // TODO
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        Bundle args = new Bundle();
        args.putInt(AttentionVolumeResultsFragment.RESULTS, wins);
        getMainActivity().toAttentionVolumeResults(args);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
