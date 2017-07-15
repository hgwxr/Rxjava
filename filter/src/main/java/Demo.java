/**
 * Created by Administrator on 2017/7/8.
 */
public class Demo {
    public static void main(String[] args) {
        String  param="abc";
        FilterChain filterChain = new FilterChain();
        filterChain.add(new EmptyIFilter(), new ValidationCallBack() {
            @Override
            public void handleErrorAction() {
                System.out.println("数据为空");
            }
        }).add(new LengthIFilter(), new ValidationCallBack() {
            @Override
            public void handleErrorAction() {
                System.out.println("数据长度小于10");
            }
        });
        boolean validationFilter = filterChain.doValidationFilter(param, filterChain);
        if (validationFilter) {
            handleApply(param);
        }
    }

    private static void handleApply(String param) {
        System.out.println(param);
    }
}
