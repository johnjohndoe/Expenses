package jimmy.dieng.expenses;

import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

/**
 * Created by jimmydieng on 15-02-05.
 */
public class ExpenseChartData extends PieChartData {

    public ExpenseChartData() {
    }

    public ExpenseChartData(List<SliceValue> values) {
        super(values);
    }

    public ExpenseChartData(PieChartData data) {
        super(data);
    }

    public float getPercentageOfValue(int index){
        SliceValue targetValue = super.getValues().get(index);
        List<SliceValue> allValues = super.getValues();

        float total = 0;
        for (SliceValue value : allValues){
            total += value.getValue();
        }

        return targetValue.getValue() / total;
    }
}
