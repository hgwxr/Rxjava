/**
 * Created by Administrator on 2017/7/8.
 */
public interface IFilterChain {
    boolean doValidationFilter(String param,IFilterChain filterChain);
}
