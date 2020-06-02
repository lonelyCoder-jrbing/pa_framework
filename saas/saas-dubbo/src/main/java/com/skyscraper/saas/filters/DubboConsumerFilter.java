package com.skyscraper.saas.filters;

import com.skyscraper.saas.constants.TraceConstant;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

import java.util.Objects;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:dubbo日志监控
 **/
@Activate
public class DubboConsumerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(TraceConstant.TRACE_ON_MDC);
        invocation.getAttachments().put(TraceConstant.TRACE_ON_MDC, Objects.nonNull(traceId) ? traceId : "");
        return invoker.invoke(invocation);
    }
}
