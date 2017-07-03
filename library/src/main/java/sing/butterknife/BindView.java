package sing.butterknife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Description:
 *
 * @author xzhang
 */

@Target(ElementType.FIELD)//设置当前注解的使用范围  变量
@Retention(RetentionPolicy.RUNTIME)//当前注解生命时长 虚拟机
public @interface BindView {
    int value();
}
