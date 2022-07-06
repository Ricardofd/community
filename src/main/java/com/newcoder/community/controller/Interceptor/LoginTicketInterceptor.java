package com.newcoder.community.controller.Interceptor;

import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CookieUtil;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie种获取凭证
        String ticket = CookieUtil.getValue(request,"ticket");
        if(ticket!=null){
            //查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //检查凭证是否有效
            if(loginTicket!=null && loginTicket.getStatus()==0 && loginTicket.getExpired().after(new Date())){
                User user =  userService.findUserById(loginTicket.getUserId());//根据凭证查询用户
                //查到之后要在之后用，需要在本次请求中持有用户,即暂存到线程对应的hostholder对象里
                hostHolder.setUsers(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user!=null && modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        hostHolder.clear();
    }
}
