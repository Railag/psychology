package com.firrael.psychology.view;

import android.os.Bundle;

import com.firrael.psychology.R;
import com.firrael.psychology.view.base.SimpleFragment;

import butterknife.OnClick;

/**
 * Created by Railag on 07.11.2016.
 */
public class TestsFragment extends SimpleFragment {

    public static TestsFragment newInstance() {

        Bundle args = new Bundle();

        TestsFragment fragment = new TestsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.landingTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_tests;
    }

    @OnClick(R.id.focusing)
    public void toFocusing() {
        getMainActivity().toInstructionFragment(InstructionFragment.Test.FOCUSING);
    }

    @OnClick(R.id.resistance)
    public void toResistance() {
        getMainActivity().toInstructionFragment(InstructionFragment.Test.STRESS_RESISTANCE);
    }

    @OnClick(R.id.stability)
    public void toStability() {
        getMainActivity().toInstructionFragment(InstructionFragment.Test.ATTENTION_STABILITY);
    }
}
