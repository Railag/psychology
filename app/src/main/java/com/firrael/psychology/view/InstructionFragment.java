package com.firrael.psychology.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firrael.psychology.R;
import com.firrael.psychology.view.base.SimpleFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Railag on 20.03.2017.
 */

public class InstructionFragment extends SimpleFragment {

    private final static String TYPE = "type";

    public enum Test {
        FOCUSING,
        ATTENTION_STABILITY,
        STRESS_RESISTANCE,
    }

    public static InstructionFragment newInstance(Test test) {

        Bundle args = new Bundle();
        args.putSerializable(TYPE, test);

        InstructionFragment fragment = new InstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.instructionText)
    TextView instructionText;

    private Test test = Test.FOCUSING;

    @Override
    protected String getTitle() {
        return getString(R.string.instruction);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_instruction;
    }

    @Override
    protected void initView(View v) {
        Bundle args = getArguments();
        if (args != null && args.containsKey(TYPE)) {
            test = (Test) args.getSerializable(TYPE);

            String instruction = "";

            switch (test) {
                case FOCUSING:
                    instruction = getString(R.string.instruction_focusing);
                    break;
                case ATTENTION_STABILITY:
                    instruction = getString(R.string.instruction_attention_stability);
                    break;
                case STRESS_RESISTANCE:
                    instruction = getString(R.string.instruction_stress_resistance);
                    break;
            }

            instructionText.setText(instruction);
        }
    }

    @OnClick(R.id.start)
    public void start() {
        switch (test) {
            case FOCUSING:
                getMainActivity().toFocusingTest();
                break;
            case ATTENTION_STABILITY:
                getMainActivity().toAttentionStabilityTest();
                break;
            case STRESS_RESISTANCE:
                getMainActivity().toStressResistanceTest();
                break;
        }
    }
}
