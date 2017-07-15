import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/8.
 */
public class FilterChain implements IFilterChain{
    private List<HashMap<IFilter,ValidationCallBack>> mFilters;
    public FilterChain(){
        mFilters=new ArrayList<>();
    }
    public FilterChain add(IFilter IFilter, ValidationCallBack callBack){
        HashMap<IFilter, ValidationCallBack> map = new HashMap<>();
        map.put(IFilter,callBack);
        mFilters.add(map);
         return this;
    }
    private  int  index;
    @Override
    public boolean doValidationFilter(String param,IFilterChain filterChain) {
        System.out.println("s doValidationFilter"+index+ this);
        if (index==mFilters.size()){
             return true;
         }
        HashMap<IFilter, ValidationCallBack> map = mFilters.get(index);
        Set<IFilter> iFilters = map.keySet();
        for (IFilter iFilter : iFilters) {
            boolean validationParam = iFilter.doValidationFilter(param);
            if (!validationParam){
                map.get(iFilter).handleErrorAction();
                return false;
            }else{
                    index++;
                   return filterChain.doValidationFilter(param, filterChain);
            }
        }
       /* for (HashMap<IFilter, ValidationCallBack> map : mFilters) {
            Set<IFilter> iFilters = map.keySet();
            for (IFilter iFilter : iFilters) {
                boolean validationParam = iFilter.doValidationFilter(param);
                if (!validationParam){
                    map.get(iFilter).handleErrorAction();
                    return false;
                }
            }
        }*/
        System.out.println("e doValidationFilter"+index+this);
        return true;
    }
}
