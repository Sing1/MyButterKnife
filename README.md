# 自定义 ButterKnife
 
## gradle:
```groovy
dependencies {
    ...
    compile 'sing.butterknife:library:1.0.1'
}
```
## Maven:
```xml
<dependency>
　　<groupId>sing.butterknife</groupId>
　　<artifactId>library</artifactId>
　　<version>1.0.1</version>
　　<type>pom</type>
</dependency>
```
自定义的 ButterKnife，目前只支持 绑定控件(BindView)和点击事件(OnClick)。
### 利用反射绑定 View
```JAVA
private static void bindView(Activity activity) throws Exception {
    // 获取类
    Class<? extends Activity> aClass = activity.getClass();
    // 获取activity中的变量
    Field[] declaredFields = aClass.getDeclaredFields();
    for(Field field : declaredFields){
        // 允许修改
        field.setAccessible(true);
        // 获取变量上的注解
        BindView annotation = field.getAnnotation(BindView.class);
        if(annotation != null){
            // 获取注解上的值
            int id = annotation.value();
            // 通过ID获取控件
            View view = activity.findViewById(id);
            // 将控件赋值给变量
            field.set(activity,view);
        }
    }
}
```
### 利用反射绑定点击事件
```JAVA
private static void bindOnClick(final Activity activity) {
    // 获取类对象
    Class<? extends Activity> aClass = activity.getClass();
    // 获取所有的方法
    Method[] declaredMethods = aClass.getDeclaredMethods();
    // 遍历所有的方法
    for(final Method method : declaredMethods){
        // 允许修改
        method.setAccessible(true);
        // 获取方法上的注解
        OnClick annotation = method.getAnnotation(OnClick.class);
        if(annotation != null){
            int id = annotation.value();// 取得注解里面的id值
            View view = activity.findViewById(id);// 绑定控件
            view.setOnClickListener(new View.OnClickListener() {// 给控件绑定点击事件
                @Override
                public void onClick(View v) {
                    try {
                        method.invoke(activity);// 调用方法
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
```
### 知识点：注解
##### 比如存在这么一个类 Student.class
```JAVA
public class Student implements Serializable {

    private String name;// 姓名
    private int age;// 年龄

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```
##### 那么获取到这个类对象的方法有3中：
```JAVA
Class clazz1 = Student.class;
Class clazz2 = Class.forName("com.xxx.Student");// 完整地址
Class clazz3 = new Student().getClass();
```
##### 获取类中的成员变量
```JAVA
// getField只能取出public
// Field fieldName = clazz.getField("name");// 会报错
// Field fieldAge = clazz.getField("age");// 会报错

//getDeclaredField可以取出private
Field fieldName = clazz.getDeclaredField("name");
Field fieldAge = clazz.getDeclaredField("age");
//允许对对象进行操作
fieldName.setAccessible(true);
fieldAge.setAccessible(true);
// 获取到的是Field对象，取出里面的值
String name = (String) fieldName.get(clazz1);
int age = fieldAge.getInt(clazz1);
		
System.out.println("name:"+name+",age:"+age);
```
##### 修改对象中变量的值
```JAVA
fieldName.set(clazz1, "lisi");
fieldAge.set(clazz1, 20);

System.out.println("name:"+clazz1.getName()+",age:"+clazz1.getAge());
```
##### 获取类中的方法
```JAVA
Method[] methods = clazz.getMethods();// 获取到所有的方法
for(Method method : methods){
    System.out.println(method.getName());
}
```
##### 获取到指定方法并调用
```JAVA
//取到setName()方法  1方法名称 2方法参数类型
Method method = clazz.getMethod("setName",String.class);
//调用该方法（在clazz1对象中调用该方法，传入姓名）
method.invoke(clazz1, "张三");
```