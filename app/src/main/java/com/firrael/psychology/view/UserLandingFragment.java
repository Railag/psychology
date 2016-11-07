package com.firrael.psychology.view;

import android.os.Bundle;
import android.widget.EditText;

import com.firrael.psychology.R;
import com.firrael.psychology.presenter.UserLandingPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 07.11.2016.
 */
@RequiresPresenter(UserLandingPresenter.class)
public class UserLandingFragment extends BaseFragment<UserLandingPresenter> {

    public static UserLandingFragment newInstance() {

        Bundle args = new Bundle();

        UserLandingFragment fragment = new UserLandingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.landingTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_landing;
    }

    @OnClick(R.id.memory)
    public void toMemory() {
        getMainActivity().toMemoryTests();
    }

    @OnClick(R.id.attention)
    public void toAttention() {
        getMainActivity().toAttentionTests();
    }

    @OnClick(R.id.perception)
    public void toPerception() {
        getMainActivity().toPerceptionTests();
    }

    @OnClick(R.id.reaction)
    public void toReaction() {
        getMainActivity().toReactionTests();
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
