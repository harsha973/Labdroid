package sha.com.ind.labapp.dump.models;

/**
 * Created by sreepolavarapu on 29/11/16.
 */

public class NamesModel extends BaseItemViewModel{
    private String mName;

    public NamesModel(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
