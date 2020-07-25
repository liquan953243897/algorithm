package com.pgz.utils.translate.core;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 控制层拦截处理类，通常情况下，在控制层返回一个统一的类结构，方便此处统一处理
 *
 * @author liquan_pgz@qq.com
 * @date 2020-07-25
 */
@ControllerAdvice
public class ResponseBody implements ResponseBodyAdvice {

    /**
     * 这里直接返回true,表示对任何handler的responseBody都调用beforeBodyWrite方法
     *
     * @param methodParameter
     * @param aClass
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-07-25
     **/
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
    *
     * @param body 控制器中方法返回值对象
     * @param methodParameter 控制器中方法对象
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
    * @return 
    * @author liquan_pgz@qq.com
    * date 2020-07-25
    **/
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        //在这里可以针对控制器中的方法的响应数据进行处理，
        //可以通过参数methodParameter过滤有翻译器注解的方法，再进行下一步处理，
        //对有翻译器注解的方法，通过参数body进行数据处理
        //调用码值翻译的工具对响应值进行处理
        return null;
    }
}
