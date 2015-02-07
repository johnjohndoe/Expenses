package jimmy.dieng.expenses;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
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
    private ExpenseChartData data;
    private ArrayList<SliceValue> pieValues;
    private ListView expenseList;
    private ExpenseAdapter expenseAdapter;


    // TODO: SAMPLE
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean hasCenterText1 = true;
    private boolean hasCenterText2 = true;
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
        generateData();

        expenseAdapter = new ExpenseAdapter(this.getActivity(), R.layout.expense_list_item, pieValues);
        expenseList = (ListView) rootView.findViewById(R.id.expense_list);
        expenseList.setAdapter(expenseAdapter);

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

        // Set up values of each arc and their spacing
        pieValues = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            sliceValue.setLabel(getCharForNumber(i + 1).toCharArray());
            pieValues.add(sliceValue);
        }

        data = new ExpenseChartData(pieValues);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        }

        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");

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
                DecimalFormat decimalFormat = new DecimalFormat("@@@@%");
                data.setCenterText1(String.valueOf(value.getLabel()));
                data.setCenterText2(decimalFormat.format(data.getPercentageOfValue(arcIndex)));

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

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
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
