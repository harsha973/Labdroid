package sha.com.ind.labapp.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreepolavarapu on 10/12/15.
 */
public class RunOOMClass {

    public void start()
    {
        List<Integer> memoryHog = new ArrayList<Integer>();
        while(true){
            memoryHog.add(new Integer(5));
        }
    }
}
