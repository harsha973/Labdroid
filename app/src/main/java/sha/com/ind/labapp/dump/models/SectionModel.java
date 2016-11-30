package sha.com.ind.labapp.dump.models;

import java.util.ArrayList;

/**
 * Created by sreepolavarapu on 29/11/16.
 */

public class SectionModel {

    private String mSectionName;
    private ArrayList<BaseItemViewModel> sectionSubListNames;

    public SectionModel(String sectionName)
    {
        mSectionName = sectionName;
        sectionSubListNames = new ArrayList<>();
    }
    public String getSectionHeaderName() {
        return mSectionName;
    }

    public ArrayList<BaseItemViewModel> getSectionSubListNames() {
        return sectionSubListNames;
    }

    public void addSubListName(BaseItemViewModel baseItemViewModel) {
        sectionSubListNames.add(baseItemViewModel);
    }
}
