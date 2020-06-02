package com.skyscraper.saas.filters;

import com.google.common.collect.ImmutableMap;
import com.skyscraper.saas.constants.TraceConstant;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

import java.util.Objects;

/**
 * create by sumerian on 2020/6/1
 * <p>
 * desc: 1.dubbo日志监控
 *       2.dubbo的扩展点应用
 **/
@Activate
public class DubboProviderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            String traceId = invocation.getAttachments().get(TraceConstant.TRACE_ON_MDC);
            MDC.setContextMap(ImmutableMap.of("traceId", Objects.isNull(traceId) ? "" : traceId));
            return invoker.invoke(invocation);
        } finally {
            MDC.clear();
        }
    }
}
