package com.paas.service.routeservice.filters;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc: token鉴权
 **/
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /***
         * 1.如果是黑名单则直接返回失败
         * 2.不需要token也不需要鉴权的白名单
         * 3.reids sso认证
         *     1>通过jwtstr拿到rediskey
         *     2>根据rediskey直接拿到redis的value
         *     3>redisvalue 判空处理
         */
        ServerHttpRequest request = exchange.getRequest();
        String methodValue = request.getMethodValue();
        if (Objects.equals("POST", methodValue)) {
            String bodyStr = resolveBodyFromRequest(request);
            log.info("bodystr:=={}", bodyStr);
            //对获取的参数进行验证，例如token和session的验证。

            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
            URI uri = request.getURI();
            ServerHttpRequest serverHttpRequest = request.mutate().uri(uri).build();
            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
            serverHttpRequest = new ServerHttpRequestDecorator(request) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            //封装request传给下一级
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());

        } else if ("GET".equals(methodValue)) {
            Map requestQueryParams = request.getQueryParams();
            //TODO 得到Get请求的请求参数后，做你想做的事
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }


    private String resolveBodyFromRequest(ServerHttpRequest request) {
        Flux<DataBuffer> body = request.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
