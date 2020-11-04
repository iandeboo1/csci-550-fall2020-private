import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Point {

    private String label;
    private List<Double> dimensions;

    public Point() {
        dimensions = new ArrayList<>();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void addDimensionValue(Double d) {
        dimensions.add(d);
    }

    public String getLabel() { return label; }

    public List<Double> getDimensions() { return dimensions; }

}




