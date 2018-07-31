package common.ref;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 父类相关的反射工具
 *
 * @author rxliuli by 2018/5/29 23:03
 */
public class SuperClassRefUtil {
    /**
     * 获取到当前类的所有父类
     * 默认返回包含 Object 并且包含自身的列表(至少有一个)
     *
     * @param clazz 当前类型
     * @return 所有父类的列表
     */
    public static List<Class<?>> getParents(Class<?> clazz) {
        return getParents(clazz, true, true);
    }

    /**
     * 获取到当前类的所有父类
     *
     * @param clazz           当前类型
     * @param isContainIfSelf 是否包含本身类型
     * @param isContainObject 是否包含 Object 类
     * @return 所有父类的列表
     */
    public static List<Class<?>> getParents(Class<?> clazz, boolean isContainIfSelf, boolean isContainObject) {
        List<Class<?>> listSuperClass = new ArrayList<>();
        if (isContainIfSelf) {
            listSuperClass.add(clazz);
        }
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null) {
            if (!isContainObject && "java.lang.Object".equals(superclass.getName())) {
                break;
            }
            listSuperClass.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return listSuperClass;
    }

    /**
     * 获取两个类的最小公共父类型
     * 默认返回包含 Object 并且包含自身的最小父类(至少有一个是 Object)
     *
     * @param classA 类型 A
     * @param classB 类型 B
     * @return 最小公共父类
     */
    public static Class<?> getMiniParent(Class<?> classA, Class<?> classB) {
        return getMiniParent(classA, classB, true, true);
    }

    /**
     * 获取两个类的最小公共父类型
     *
     * @param classA          类型 A
     * @param classB          类型 B
     * @param isContainIfself 是否包含本身类型
     * @param isContainObject 是否包含 Object 类
     * @return 最小公共父类
     */
    public static Class<?> getMiniParent(Class<?> classA, Class<?> classB, boolean isContainIfself, boolean isContainObject) {
        List<Class<?>> classAParents = getParents(classA, isContainIfself, isContainObject);
        Set<Class<?>> classBParents = new HashSet<>(getParents(classB, isContainIfself, isContainObject));
        for (Class<?> classAParent : classAParents) {
            if (classBParents.contains(classAParent)) {
                return classAParent;
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行 clazz = clazz.getSuperclass(), 最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符 (private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     * @return 父类中方法的执行结果
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        // 根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        // 抑制 Java 对方法进行检查, 主要是针对私有方法而言
        method.setAccessible(true);

        try {
            if (null != method) {
                // 调用 object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行 clazz = clazz.getSuperclass(), 最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */
    public static boolean setFieldValue(Object object, String fieldName, Object value) {
        // 根据 对象和属性名通过反射 调用上面的方法获取 Field 对象
        Field field = getDeclaredField(object, fieldName);
        // 抑制 Java 对其的检查
        field.setAccessible(true);
        try {
            // 将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
            return true;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return false;
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {

        // 根据 对象和属性名通过反射 调用上面的方法获取 Field 对象
        Field field = getDeclaredField(object, fieldName);
        // 抑制 Java 对其的检查
        field.setAccessible(true);
        try {
            // 获取 object 中 field 所代表的属性值
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取类型以及父类型中全部的字段
     * 注: 默认消除重复字段
     *
     * @param clazz 要获取的类型
     * @return 类型以及父类型中全部的字段
     */
    public static Set<Field> getAllDeclaredField(Class<?> clazz) {
        return getParents(clazz, true, false).stream()
                .flatMap(tClass -> Arrays.stream(tClass.getDeclaredFields()))
                .collect(Collectors.toSet());
    }

    /**
     * 获取类型以及父类型中全部的实例方法
     * 注: 默认消除重复方法
     *
     * @param clazz 要获取的类型
     * @return 类型以及父类型中全部的方法
     */
    public static Set<Method> getAllDeclaredMethod(Class<?> clazz) {
        return getParents(clazz, true, false).stream()
                .flatMap(tClass -> Arrays.stream(tClass.getDeclaredMethods()))
                .collect(Collectors.toSet());
    }
}
