package jimmy.dieng.expenses;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ExpenseFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private PieChartView expensesPieChart;
    private PieChartData data;
    List<SliceValue> pieValues;

    // TODO: SAMPLE
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = true;
    private boolean hasCenterCircle = true;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploaded = false;
    private boolean hasArcSeparated = true;
    private boolean hasLabelForSelected = true;
    // TODO: END SAMPLE

    // TODO: Decide if arguments are needed
    public static ExpenseFragment newInstance(String param1, String param2) {
        ExpenseFragment fragment = new ExpenseFragment();

        // TODO: Decide if arguments are needed
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExpenseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);
        expensesPieChart = (PieChartView) rootView.findViewById(R.id.pie_expense);
        pieValues = new ArrayList<SliceValue>();
        generateData();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // TODO: Uncomment when using callbacks
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
//        public void onFragmentInteraction(String id);
    }

    // TODO: SAMPLE
    private void generateData() {
        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());

            if (isExploaded) {
                sliceValue.setSliceSpacing(24);
            }

            if (hasArcSeparated && i == 0) {
                sliceValue.setSliceSpacing(32);
            }

            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        }

        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");

            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
        }

        expensesPieChart.setPieChartData(data);
        expensesPieChart.setChartRotationEnabled(false);
        expensesPieChart.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            int lastOpenSlice = -1;

            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                toggleSliceSpacing(arcIndex);

                // Keep track of this arc as its the only one open
                if (lastOpenSlice == -1) {
                    lastOpenSlice = arcIndex;
                }
                // Else there is another arc to close
                else {
                    // If it is the same arc selected then we have already closed it
                    if (lastOpenSlice == arcIndex) {
                        lastOpenSlice = -1;
                    }

                    // Else we have to close it
                    else {
                        toggleSliceSpacing(lastOpenSlice);
                        lastOpenSlice = arcIndex;
                    }
                }
            }

            @Override
            public void onValueDeselected() {
            }
        });
    }
    // TODO: END SAMPLE

    private void toggleSliceSpacing(int arcIndex) {
        SliceValue slice = data.getValues().get(arcIndex);
        toggleSliceSpacing(slice);
    }

    private void toggleSliceSpacing(SliceValue slice) {
        if (slice.getSliceSpacing() == 24) {
            slice.setSliceSpacing(0);
        } else {
            slice.setSliceSpacing(24);
        }
    }
}
