package sing;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import sing.butterknife.BindView;
import sing.butterknife.OnClick;

public class ButterKnife {

    public static void bind(Activity activity) {
        try {
            bindView(activity);
            bindOnClick(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定View
     */
    private static void bindView(Activity activity) throws Exception {
        Class<? extends Activity> aClass = activity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for(Field field : declaredFields){
            field.setAccessible(true);
            BindView annotation = field.getAnnotation(BindView.class);
            if(annotation != null){
                int id = annotation.value();
                View view = activity.findViewById(id);
                field.set(activity,view);
            }
        }
    }

    /**
     * 绑定点击事件
     */
    private static void bindOnClick(final Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for(final Method method : declaredMethods){
            method.setAccessible(true);
            OnClick annotation = method.getAnnotation(OnClick.class);
            if(annotation != null){
                int id = annotation.value();
                View view = activity.findViewById(id);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.invoke(activity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}