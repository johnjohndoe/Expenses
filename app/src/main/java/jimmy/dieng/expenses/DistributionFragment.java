package jimmy.dieng.expenses;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class DistributionFragment extends Fragment {
    private View rootView;
    private OnFragmentInteractionListener mListener;
    private PieChartView distributionPieChart;
    private DistributionChartData data;
    private ArrayList<SliceValue> pieValues;
    private ListView distributionList;
    private DistributionAdapter distributionAdapter;
    private View currentSelectedView;
    private PieChartOnValueSelectListener pieChartListener;
    private int lastOpenSlice = -1;


    // TODO: SAMPLE
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean hasCenterText1 = true;
    private boolean hasCenterText2 = true;
    private boolean hasLabelForSelected = true;
    // TODO: END SAMPLE


    public static DistributionFragment newInstance() {
        DistributionFragment fragment = new DistributionFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DistributionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_distribution, container, false);
        setUpUI();

        return rootView;
    }

    private void setUpUI() {
        // Set up the pie chart view
        distributionPieChart = (PieChartView) rootView.findViewById(R.id.pie_distribution);
        pieChartListener = new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                performBindedClick(arcIndex);
            }

            @Override
            public void onValueDeselected() {
            }
        };
        generateData();

        // Set up the list view
        distributionAdapter = new DistributionAdapter(this.getActivity(), R.layout.distribution_list_item, pieValues);
        distributionList = (ListView) rootView.findViewById(R.id.distribution_list);
        distributionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pieChartListener.onValueSelected(position, pieValues.get(position));
                distributionPieChart.animationDataFinished();
            }
        });
        distributionList.setAdapter(distributionAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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

        data = new DistributionChartData(pieValues);
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

        distributionPieChart.setPieChartData(data);
        distributionPieChart.setChartRotationEnabled(false);
        distributionPieChart.setOnValueTouchListener(pieChartListener);
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
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

    private void unhighlightCurrentRow(View rowView) {
        rowView.setBackgroundColor(getResources().getColor(R.color.white_cloud));
    }

    private void highlightCurrentRow(View rowView) {
        rowView.setBackgroundColor(getResources().getColor(R.color.gray_silver));
    }

    private void performBindedClick(int arcIndex) {
        toggleSliceSpacing(arcIndex);
        DecimalFormat decimalFormat = new DecimalFormat("@@@@%");
        data.setCenterText1(String.valueOf(pieValues.get(arcIndex).getLabel()));
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

        if (currentSelectedView != null && currentSelectedView != distributionList.getChildAt(arcIndex)) {
            unhighlightCurrentRow(currentSelectedView);
        }
        currentSelectedView = distributionList.getChildAt(arcIndex);
        highlightCurrentRow(currentSelectedView);
    }

}
