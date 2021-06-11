package cn.itcast.travel.web.servlet.del;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User u = new User();
        try {
            BeanUtils.populate(u, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        User user = service.login(u);
        ResultInfo info = new ResultInfo();
        if (null == user) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if (null != user && !"Y".equals(user.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活，请激活把");
        }
        if (null != user && "Y".equals(user.getStatus())) {
            request.getSession().setAttribute("user",user);//登录成功标记
            info.setFlag(true);
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
