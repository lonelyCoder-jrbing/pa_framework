package utils.reflect;

import java.lang.reflect.Field;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:
 **/
@FunctionalInterface
public interface ObjCallBack {

    void doWith(Object obj, Field field) throws RuntimeException;
}
