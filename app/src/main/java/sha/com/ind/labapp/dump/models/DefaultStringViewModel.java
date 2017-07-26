package sha.com.ind.labapp.dump.models;

import java.util.Random;

/**
 * Created by sreepolavarapu on 15/06/16.
 */
public class DefaultStringViewModel extends BaseViewModel{

    private String mText;

    public DefaultStringViewModel()
    {
        mText = String.valueOf(new Random().nextInt(1000));
    }

    public String getText() {
        return mText;
    }
}
