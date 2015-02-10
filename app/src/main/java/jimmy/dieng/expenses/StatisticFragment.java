package jimmy.dieng.expenses;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;


public class StatisticFragment extends Fragment {
    private View rootView;
    private LineChartData monthlyData;
    private LineChartView statsMonthlyChart;
    private LineChartData dailyData;
    private LineChartView statsDailyChart;

    private int numberOfPoints = 12;

    float[] monthlyDataPoints = new float[numberOfPoints];
    float[] dailyDataPoints = new float[numberOfPoints];

    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean hasLabels = true;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance() {
        StatisticFragment fragment = new StatisticFragment();
        return fragment;
    }

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_statistic, container, false);
        setUpUI();
        return rootView;
    }

    private void setUpUI(){
        statsMonthlyChart = (LineChartView) rootView.findViewById(R.id.statistic_month_chart);
        statsDailyChart = (LineChartView) rootView.findViewById(R.id.statistic_daily_chart);
        getDataPoints();
        getMonthlyLineData(statsMonthlyChart);
        getDailyLineData(statsDailyChart);
        statsMonthlyChart.setZoomType(ZoomType.HORIZONTAL);
        statsMonthlyChart.setZoomLevel(0, monthlyDataPoints[0], 3);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getDataPoints(){
        for (int i = 0; i < numberOfPoints; i++){
            monthlyDataPoints[i] = (float) Math.random() * 100f;
        }

        for (int i = 0; i < numberOfPoints; i++){
            dailyDataPoints[i] = (float) Math.random() * 100f;
        }
    }

    private void getMonthlyLineData(LineChartView chartView){
        List<Line> lines = generateLines(monthlyDataPoints);
        addBudgetLine(lines, 100);

        monthlyData = new LineChartData(lines);

        // Set up the X Axis Labels
        List<Float> axisValues = new ArrayList<Float>();
        for (int i = 0; i < numberOfPoints; i++){
            axisValues.add(Float.valueOf(i));
        }

        List<String> axisValueLabels = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        Calendar calendar = new GregorianCalendar();
        for (int i = 0; i < 12; i++){
            calendar.set(Calendar.MONTH, i);
            axisValueLabels.add(sdf.format(calendar.getTime()));
        }
        Axis axisX = Axis.generateAxisFromCollection(axisValues, axisValueLabels);

        Axis axisY = new Axis().setHasLines(true);
        monthlyData.setAxisXBottom(axisX);
        monthlyData.setAxisYLeft(axisY);

        monthlyData.setBaseValue(Float.NEGATIVE_INFINITY);
        chartView.setLineChartData(monthlyData);
        chartView.setZoomEnabled(false);
    }

    private List<Line> generateLines(float [] dataPoints) {
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();

        for (int i = 0; i < numberOfPoints; i++){
            values.add(new PointValue(i, dataPoints[i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN);
        line.setShape(shape);
        line.setHasLabels(hasLabels);
        line.setHasLines(hasLines);
        line.setHasPoints(hasPoints);
        lines.add(line);
        return lines;
    }

    private void addBudgetLine(List<Line> lines, float budget) {
        List<PointValue> budgetValue = new ArrayList<PointValue>();
        budgetValue.add(new PointValue(0, budget));
        budgetValue.add(new PointValue(numberOfPoints - 1, budget));

        Line budgetLine = new Line(budgetValue);
        budgetLine.setHasPoints(false);
        budgetLine.setColor(ChartUtils.COLOR_RED);
        lines.add(budgetLine);
    }

    private void getDailyLineData(LineChartView chartView){
        List<Line> lines = generateLines(dailyDataPoints);
        lines.get(0).setCubic(true);

        dailyData = new LineChartData(lines);

        // Set up the X Axis Labels
        List<Float> axisValues = new ArrayList<Float>();
        for (int i = 0; i < numberOfPoints; i++){
            axisValues.add(Float.valueOf(i));
        }

        List<String> axisValueLabels = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        Calendar calendar = new GregorianCalendar();
        for (int i = 0; i < 12; i++){
            calendar.set(Calendar.MONTH, i);
            axisValueLabels.add(sdf.format(calendar.getTime()));
        }
        Axis axisX = Axis.generateAxisFromCollection(axisValues, axisValueLabels);

        Axis axisY = new Axis().setHasLines(true);
        monthlyData.setAxisXBottom(axisX);
        monthlyData.setAxisYLeft(axisY);

        monthlyData.setBaseValue(Float.NEGATIVE_INFINITY);
        chartView.setLineChartData(dailyData);
        chartView.setZoomEnabled(false);
    }

}
