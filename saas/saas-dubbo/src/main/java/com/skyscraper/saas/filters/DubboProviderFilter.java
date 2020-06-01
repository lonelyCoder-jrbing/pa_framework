package com.skyscraper.saas.filters;

import com.skyscraper.saas.constants.TraceConstant;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

/**
 * create by sumerian on 2020/6/1
 * <p>
 * desc:
 **/
public class DubboProviderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            String traceId = invocation.getAttachments().get(TraceConstant.TRACE_ON_MDC);

//            MDC.setContextMap();
        } finally {

        }


        return null;
    }
}
