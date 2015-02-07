package jimmy.dieng.expenses.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.dieng.expenses.R;
import jimmy.dieng.expenses.settings.Credit;

/**
 * Created by jimmydieng on 15-01-31.
 */
public class CreditArrayAdapter extends ArrayAdapter<Credit> {
    private ArrayList<Credit> mAllCredits;
    private Context mContext;

    static class CreditHolder {
        public TextView mCreditTitle;
        public TextView mCreditDesc;
    }

    public CreditArrayAdapter(Context context, int resource, ArrayList<Credit> credits) {
        super(context, resource, credits);
        mContext = context;
        mAllCredits = credits;
    }

    @Override
    public View getView(int position, View creditView, ViewGroup parent) {
        CreditHolder holderView;

        if (creditView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            creditView = inflater.inflate(R.layout.credit_entry, parent, false);

            holderView = new CreditHolder();
            holderView.mCreditTitle = (TextView) creditView.findViewById(R.id.credit_item_title);
            holderView.mCreditDesc = (TextView) creditView.findViewById(R.id.credit_item_desc);

            creditView.setTag(holderView);
        } else {
            holderView = (CreditHolder) creditView.getTag();
        }

        Credit currentCredit = mAllCredits.get(position);
        holderView.mCreditTitle.setText(currentCredit.getTitle());
        holderView.mCreditDesc.setText(currentCredit.getDesc());

        return creditView;
    }
}
