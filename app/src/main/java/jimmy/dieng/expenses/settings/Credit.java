package jimmy.dieng.expenses.settings;

/**
 * Created by jimmydieng on 15-01-31.
 */
public class Credit {
    String mTitle;
    String mDesc;

    public Credit(CharSequence title, CharSequence desc) {
        mTitle = String.valueOf(title);
        mDesc = String.valueOf(desc);
    }


    public Credit(String title, String desc) {
        mTitle = String.valueOf(title);
        mDesc = String.valueOf(desc);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDesc() {
        return mDesc;
    }
}
