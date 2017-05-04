package com.firrael.psychology.view.results;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firrael.psychology.R;
import com.firrael.psychology.Utils;
import com.firrael.psychology.view.base.SimpleFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;

/**
 * Created by Railag on 19.04.2017.
 */

public class ComplexMotorReactionResultsFragment extends SimpleFragment implements ResultScreen {

    public final static String WINS = "wins";
    public final static String FAILS = "fails";

    public static ComplexMotorReactionResultsFragment newInstance(Bundle args) {

        ComplexMotorReactionResultsFragment fragment = new ComplexMotorReactionResultsFragment();
        fragment.setHasOptionsMenu(true);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.winsNumber)
    TextView winsNumber;

    @BindView(R.id.failsNumber)
    TextView failsNumber;

    @Override
    protected String getTitle() {
        return getString(R.string.resultsTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.results, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
            default:
                save();
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4.asLandscape())
                .setResolution(new PrintAttributes.Resolution("Attention Volume results", "Attention Volume results", 300, 300))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build();

        PrintedPdfDocument document = new PrintedPdfDocument(getActivity(),
                attributes);

        PdfDocument.Page page = document.startPage(0);

        View content = getView();
        content.draw(page.getCanvas());

        document.finishPage(page);

        if (!canWriteOnExternalStorage() || !Utils.canWrite(getActivity())) {
            // TODO
            return;
        }

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/psychology");
        dir.mkdir();

        File newFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/psychology/complex_motor_reaction_results.pdf");

        String name = newFile.getAbsolutePath();

        try {
            OutputStream stream =
                    new FileOutputStream(newFile, true);
            document.writeTo(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();

        Toast.makeText(getActivity(), "Results saved to " + name, Toast.LENGTH_SHORT).show();
    }

    public static boolean canWriteOnExternalStorage() {
        // get the state of your external storage
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // if storage is mounted return true
            return true;
        }
        return false;
    }

    @Override
    protected int getViewId() {
        return R.layout.results_complex_motor_reaction_layout;
    }

    @Override
    protected void initView(View v) {
        Bundle args = getArguments();

        Utils.verifyStoragePermissions(getActivity());

        if (args != null) {
            if (args.containsKey(WINS)) {
                int wins = args.getInt(WINS);

                winsNumber.setText(String.valueOf(wins));
            }

            if (args.containsKey(FAILS)) {
                int fails = args.getInt(FAILS);

                failsNumber.setText(String.valueOf(fails));
            }

        }
    }
}
