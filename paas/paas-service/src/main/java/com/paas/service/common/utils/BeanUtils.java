package com.paas.service.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.JSONUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/
public class BeanUtils {

    private static <T> Optional<T> copyWithOpt(Object src, Class<T> targetClass) {
        return Optional.ofNullable(copy(src, targetClass));
    }

    public static <T> T copy(Object src, Class<T> targetClass) {
        if (Objects.isNull(src)) return null;
        try {
            return JSON.parseObject(JSONUtils.serializeObject(src), targetClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
