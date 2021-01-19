package com.lsqingfeng.action.web.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @className: ControllerLogAspect
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 17:04
 */
@Component
@Aspect
@Slf4j
public class ControllerLogAspect {

    /**
     * 用来记录请求进入的时间，防止多线程时出错，这里用了ThreadLocal
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 扫描controller层接口
     * 以下几种表达式均可用:
     * 1、execution(public * com.cestc..controller.*.*(..))
     * 表示可扫描com.cestc包及其子包下的controller包下的任意类的任意方法
     * 2、execution(public * com.cestc..*.*Controller.*(..))
     * 表示可扫描com.cestc包及其子包下以Controller结尾的类的任意方法
     * 3、@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)
     * 表示可以扫描@RestController和@Controller注解的执行方法，其中的@within也可以换成@target、@annotation
     */
    @Pointcut("execution(public * com.lsqingfeng..controller.*.*(..))")
    public void log() {
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 获取请求ip
            String ip = getIpAddr(request);
            // 获取请求Url
            String requestUrl = request.getRequestURL().toString();
            // 获取类名
            String className = point.getTarget().getClass().getName();
            // 获取执行的方法名称
            String methodName = point.getSignature().getName();
            // 获取请求参数,处理成json格式
            // body中参数
            Object[] pointArgs = point.getArgs();
            //过滤请求参数数组中携带有Request或者Response对象，解决fastjson序列化异常
            Stream<?> stream = ArrayUtils.isEmpty(pointArgs) ? Stream.empty() : Arrays.asList(pointArgs).stream();
            List<Object> bodyParams = stream
                    .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                    .collect(Collectors.toList());
            // url后拼参数
            String urlParam = request.getQueryString();
            // 拼接参数
            String reqParam = "<urlParam>:" + urlParam + " <--> ";
            if (bodyParams.size() > 0) {
                Object bodyParam = bodyParams.get(0);
                if (!(bodyParam instanceof File || bodyParam instanceof MultipartFile)) {
                    reqParam += "<bodyParam>: " + JSON.toJSONString(bodyParam);
                }
            } else {
                reqParam += "<bodyParam>:" + null;
            }
            // 处理header
            StringBuilder headerBuf = new StringBuilder();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = request.getHeader(key);
                if (headerBuf.length() > 0) {
                    headerBuf.append(" | ");
                }
                headerBuf.append(key).append("=").append(value);
            }
            // 打印请求参数参数,记录开始时间
            startTime.set(System.currentTimeMillis());
            log.info("<-请求-> 请求时间:{} | ip:{} | 请求接口:{} | 参数:{} | 请求类:{} | 方法:{} | 请求header:{}", startTime.get(), ip, requestUrl, reqParam, className, methodName, headerBuf.toString());
            // 执行目标方法,获取执行结果
            Object result = point.proceed();
            // 获取执行完的时间 打印返回报文
            log.info("<-正常返回-> 请求时间:{} | 请求接口:{} | 方法:{} | 处理时间:{} 毫秒 | 返回结果:{}", startTime.get(), requestUrl, methodName, (System.currentTimeMillis() - startTime.get()), JSON.toJSONString(result));
            return result;
        } finally {
            startTime.remove();
        }
    }

    /**
     * 获取ip
     */
    public String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";
        String ipAddress;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            // "***.***.***.***".length()= 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        // 或者这样也行,对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        return ipAddress;
    }

}
