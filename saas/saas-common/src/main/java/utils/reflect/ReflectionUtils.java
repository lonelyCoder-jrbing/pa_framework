package utils.reflect;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:
 **/
@Slf4j
public class ReflectionUtils extends org.springframework.util.ReflectionUtils {

    /***
     *  实例化对象，必须要有默认的构造方法
     * @param clazz  被实例化的对象的字节码
     * @param <T>
     * @return
     */
    public static <T> T instance(Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            log.info("实例化失败", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void doObjWith(Object obj, ObjCallBack fc) {
        iteratorObj(obj, fc);
    }

    private static void iteratorObj(Object obj, ObjCallBack fc) {
        if (BeanUtils.isSimpleProperty(obj.getClass())) {
            fc.doWith(obj, null);
            return;
        }
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            if (null == collection && collection.size() == 0) {
                return;
            }
            for (Object aCollection : collection) {
                iteratorObj(aCollection, fc);
            }
            return;
        }
        Class<?> clazz = obj.getClass();
        //对象
        doWithFields(clazz, field -> {
            makeAccessible(field);
            //简单类型
            if (BeanUtils.isSimpleProperty(field.getType())) {
                fc.doWith(obj, field);
                return;
            }
            //非简单类型
            Object val = field.get(obj);
            iteratorObj(val, fc);
        });
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private int age;
    }

    public static void main(String[] args) {
        User user01 = new User("jurongbing01", 12);
        User user02 = new User("jurongbing02", 13);
        List<User> users = Lists.newArrayList(user01, user02);
        Object obj = users;
        doObjWith(obj,(object,feilds)->{
            log.info("object:{}",object);
            log.info("feilds:{}",feilds);
        });
    }
}
