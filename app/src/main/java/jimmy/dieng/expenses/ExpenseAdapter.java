package jimmy.dieng.expenses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.SliceValue;

/**
 * Created by jimmydieng on 15-02-07.
 */
public class ExpenseAdapter extends ArrayAdapter {
    private ArrayList<SliceValue> mExpenses;
    private Context mContext;

    public ExpenseAdapter(Context context, int resource, ArrayList<SliceValue> objects) {
        super(context, resource, objects);
        mContext = context;
        mExpenses = objects;
    }

    static class ExpenseHolder {
        public TextView mTitle;
        public TextView mValue;
        public TextView mPercentage;
    }

    @Override
    public View getView(int position, View expenseView, ViewGroup parent) {
        ExpenseHolder holderView;

        if (expenseView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            expenseView = inflater.inflate(R.layout.expense_list_item, parent, false);

            holderView = new ExpenseHolder();
            holderView.mTitle = (TextView) expenseView.findViewById(R.id.textview_expense_title);
            holderView.mValue = (TextView) expenseView.findViewById(R.id.textview_expense_value);
            holderView.mPercentage = (TextView) expenseView.findViewById(R.id.textview_expense_percent);

            expenseView.setTag(holderView);
        } else {
            holderView = (ExpenseHolder) expenseView.getTag();
        }

        SliceValue expense = mExpenses.get(position);
        holderView.mTitle.setText(expense.getLabel(), 0, expense.getLabel().length);
        holderView.mValue.setText(String.valueOf(expense.getValue()));

        // TODO: Implement a get percentage method
        holderView.mPercentage.setText("10");

        return expenseView;
    }


}
