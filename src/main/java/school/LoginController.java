package school;

import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=UTF-8");
        JSONObject message = new JSONObject();
        try{
            User loggedUser = UserService.getInstance().login(username,password);
            if(loggedUser!=null){
                message.put("message","登陆成功");
                HttpSession session = request.getSession();
                //10分钟没有操作，则使session失败
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("currentUser",loggedUser);
                response.getWriter().println(message);
                //此处应重定向到索引页（菜单页）
                return;
            }else{
                message.put("message","用户名或密码错误");
            }
        }catch(SQLException e){
            message.put("message","数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("mrssage","网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
