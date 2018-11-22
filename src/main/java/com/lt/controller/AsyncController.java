package com.lt.controller;


import com.lt.service.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

@Controller
public class AsyncController {

    @ResponseBody
    @RequestMapping("/createOrder")
    public DeferredResult<Object> creaatOrder(){

        DeferredResult<Object> deferredResult = new DeferredResult(3000L,"创建失败");

        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create(){

        //创建订单
        String s = UUID.randomUUID().toString();
        DeferredResult<Object> objectDeferredResult = DeferredResultQueue.get();
        objectDeferredResult.setResult(s);
        return "success ====== >>>" + s;
    }




    /**
     * 1.控制器返回Callable
     * 2.SpringMVC 异步处理：将Callable  提交到 TaskExecutor 使用一个隔离的线程进行执行
     * 3.DispatcherServlet 和 所有的Filter 退出web 容器的线程，但是response 保持打开
     * 4.Callable 返回结果，SpringMVC 将请求重新派发给容器，恢复之前的处理
     * 5.根据Callable 返回的结果。SpringMVC 继续进行视图渲染流程等（从收请求-视图渲染）
     *
     *
     * preHandle/async01
     * ==============================================================
     * 主线程。。start。。。Thread[http-nio-8080-exec-4,5,main]1542914594014
     * 主线程。。end。。。Thread[http-nio-8080-exec-4,5,main]1542914594017
     * =============DispatcherServlet 以及所有的Filter退出线程 ======================
     *
     * =======================等待Callable执行==============================
     * 子线程。。start。。。Thread[MvcAsync1,5,main]1542914594030
     * 子线程。。ed。。。Thread[MvcAsync1,5,main]1542914596031
     *
     * ============================================================
     * preHandle/async01
     * afterCompletion......
     *
     *
     * 异步的拦截器：
     *      1.原生的拦截器：AsyncListener
     *      2.Spring MVC :实现AsyncHandlerInterceptor
     *
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/async01")
    public Callable<String> async01(){

        System.out.println("主线程。。start。。。" + Thread.currentThread()+ System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {

            public String call() throws Exception {
                System.out.println("子线程。。start。。。" + Thread.currentThread()+ System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("子线程。。ed。。。" + Thread.currentThread()+ System.currentTimeMillis());
                return "Callable<String> async01()";
            }
        };
        System.out.println("主线程。。end。。。" + Thread.currentThread()+ System.currentTimeMillis());
        return callable;
    }






}
