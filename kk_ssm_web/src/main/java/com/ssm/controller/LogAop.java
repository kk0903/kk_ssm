package com.ssm.controller;

import com.ssm.domain.SysLog;
import com.ssm.service.ISysLogService;
import com.ssm.service.impl.ISysLogServiceImpl;
import org.aspectj.lang.annotation.After;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextListener;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysLogService;

    private Date startTime;
    // 访问时间
    private Class executionClass;// 访问的类
    private Method executionMethod; // 访问的方法 // 主要获取访问时间、访问的类、访问的方法

    @Before("execution(* com.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException, SecurityException {
        startTime = new Date();//访问时间
        //获取访问的类
        executionClass = jp.getTarget().getClass();
        //获取访问的方法
        String methodName = jp.getSignature().getName();

        Object[] args = jp.getArgs();
        if (args == null || args.length == 0) {//无参数
            executionMethod = executionClass.getMethod(methodName);
        } else {
            //有参数就便利args所有元素，将其撞到一个class数组中
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            executionMethod = executionClass.getMethod(methodName, classArgs);
        }

    }

    @After("execution(* com.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        long time = new Date().getTime() - startTime.getTime();//获取访问时长

        String url = "";

        //获取url
        if (executionClass != null && executionMethod != null & executionClass != LogAop.class) {
            //获取类上的@requestMapping
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();
                //获取方法上的requestMapping
                RequestMapping methodAnnotation = executionMethod.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];
                }
            }
            //获取当前用户名 从spring提供的上下文中获取当前登录用户
            SecurityContext context = SecurityContextHolder.getContext();

            User user = (User) context.getAuthentication().getPrincipal();

            String username = user.getUsername();

            /*获取当前ip*/
            String ip = request.getRemoteAddr();

            /*将相关信息封装到SysLog对象中*/
            SysLog sysLog = new SysLog();
            sysLog.setExecutionTime(time);
            sysLog.setIp(ip);
            sysLog.setUrl(url);
            sysLog.setUsername(username);
            sysLog.setVisitTime(startTime);
            sysLog.setMethod("[类名]" + executionClass.getName() + "，[方法名]" + executionMethod.getName());

            /*调用service完成日志写入*/
            sysLogService.save(sysLog);
        }
    }
}

