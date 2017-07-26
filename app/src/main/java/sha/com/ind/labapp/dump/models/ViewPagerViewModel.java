package sha.com.ind.labapp.dump.models;

import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sreepolavarapu on 15/06/16.
 */
public class ViewPagerViewModel extends BaseViewModel{

    ArrayList<String> stringsAL;

    public ViewPagerViewModel()
    {
        stringsAL = new ArrayList<>();
        int maxSize = new Random(20).nextInt();

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            stringsAL.add(String.valueOf(random.nextFloat()));
        }
    }

    public ArrayList<String> getStringsAL() {
        return stringsAL;
    }
}
