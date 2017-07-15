/**
 * Created by Administrator on 2017/7/8.
 */
public class EmptyIFilter implements IFilter {
    @Override
    public boolean doValidationFilter(String param) {
        boolean isProcess = null != param && !param.isEmpty();
        return isProcess;
    }
}
