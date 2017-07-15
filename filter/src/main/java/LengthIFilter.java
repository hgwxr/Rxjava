/**
 * Created by Administrator on 2017/7/8.
 */
public class LengthIFilter implements  IFilter {
    @Override
    public boolean doValidationFilter(String param) {
        return param.length()>10;
    }
}
