package config;

import org.slf4j.MDC;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * create by sumerian on 2020/6/17
 * <p>
 * desc:
 **/
public class TraceInterceptor implements ClientHttpRequestInterceptor, PriorityOrdered {

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String traceId = restTemplateTraceHeader(request);


        return null;
    }

    private String restTemplateTraceHeader(HttpRequest request) {
        List<String> headers = request.getHeaders().get("traceId");
        if (null != headers && headers.size() > 0) {
            return "";
        }
        return headers.get(0);
    }

    private String traceId() {
        String traceId = MDC.get("traceId");
        return traceId==null? UUID.randomUUID().toString().replaceAll("-",""):traceId;
    }
}
