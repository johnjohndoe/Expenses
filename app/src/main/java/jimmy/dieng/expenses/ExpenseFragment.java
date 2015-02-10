package jimmy.dieng.expenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseFragment extends Fragment {
    View rootView;
    ColumnChartView expenseChart;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExpenseFragment.
     */
    public static ExpenseFragment newInstance() {
        ExpenseFragment fragment = new ExpenseFragment();
        return fragment;
    }

    public ExpenseFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_expense, container, false);
        setUpUI();
        return rootView;
    }

    private void setUpUI() {
        expenseChart = (ColumnChartView) rootView.findViewById(R.id.expense_chart);
    }


}
