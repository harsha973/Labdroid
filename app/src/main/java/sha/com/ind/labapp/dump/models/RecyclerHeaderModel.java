package sha.com.ind.labapp.dump.models;

/**
 * Created by sreepolavarapu on 29/11/16.
 */

public class RecyclerHeaderModel extends BaseItemViewModel {

    private String headerText;

    public RecyclerHeaderModel(String headerText)
    {
        this.headerText = headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getHeaderText() {
        return headerText;
    }
}
