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
import com.firrael.psychology.presenter.ReactionWhiteTestPresenter;

import java.text.DecimalFormat;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

import java.util.Date;

/**
 * Created by Railag on 07.11.2016.
 */
@RequiresPresenter(ReactionWhiteTestPresenter.class)
public class ReactionWhiteTestFragment extends BaseFragment<ReactionWhiteTestPresenter> {

    @BindView(R.id.whiteTestText)
    TextView text;

    @BindView(R.id.whiteTestBackground)
    View background;

    private long time;

    public static ReactionWhiteTestFragment newInstance() {

        Bundle args = new Bundle();

        ReactionWhiteTestFragment fragment = new ReactionWhiteTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.reactionWhiteTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_test_reaction_white;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Handler handler = new Handler();
        int startTime = new Random().nextInt(5000);

        handler.postDelayed(() -> {
            stopLoading();
            action();
        }, startTime);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.whiteTestBackground)
    public void click() {

        if (text.getVisibility() == View.GONE) {
            long currTime = System.nanoTime();
            long diff = currTime - time;
            String diffInSeconds = new DecimalFormat("#.##").format(diff / 1000000000.0);
            String result = diffInSeconds + " секунд";
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            toNextTest();
        }

    }

    private void toNextTest() {
        // TODO
    }

    private void action() {
        text.setVisibility(View.GONE);
        background.setBackgroundColor(getResources().getColor(android.R.color.white));
        time = System.nanoTime();
    }

   /* public void onSuccess(UserResult result) {
        stopLoading();
        if (result == null) {
            onError(new IllegalArgumentException());
            return;
        }
        if (result.invalid()) {
            toast(result.error);
            return;
        }
        toast("success login");
        User.save(result, getActivity());
        getMainActivity().updateNavigationMenu();
        getMainActivity().toUserLandingScreen();
    }

    public void onError(Throwable error) {
        error.printStackTrace();
        stopLoading();
        toast(error.getMessage());
    }*/
}
