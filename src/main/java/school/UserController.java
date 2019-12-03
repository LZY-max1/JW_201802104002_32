package school;

import domain.User;
import service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_json = JsonUtil.getJson(request);
        JSONObject jsonObject = JSONObject.parseObject(user_json);
        User userToAdd = JSON.parseObject(user_json, User.class);
        response.setContentType("text/html;charset=UTF8");
        JSONObject message = new JSONObject();
        try {
            UserService.getInstance().add(userToAdd);
        } catch (SQLException e) {
            message.put("message","数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message","网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String id_str = request.getParameter("id");
        String username_str = request.getParameter("username");
        JSONObject message = new JSONObject();


        try {
            if (username_str != null){
                this.responseDepartmentsByUsername(username_str,response);
            } else {
                this.responseUsers(response);
            }

        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        JSONObject message = new JSONObject();
        try {
            UserService.getInstance().delete(id);
            message.put("message","删除成功");
            response.getWriter().println(message);
        } catch (SQLException e) {

            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       /* response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String user_json = JsonUtil.getJson(request);
        User userToAdd = JSON.parseObject(user_json, User.class);
        JSONObject message = new JSONObject();
        try {
            UserService.getInstance().update(userToAdd);
            message.put("message","修改成功");
            response.getWriter().println(message);
        } catch (SQLException e) {

            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }

        */

        //login
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        JSONObject message = new JSONObject();
        User user = null;
        try {
            user = UserService.getInstance().login(username,password);
            if (user != null){

                message.put("message","登录成功");
                response.getWriter().println(message);
           }else {

                message.put("message","账户或密码错误");
                response.getWriter().println(message);
            }

        } catch (SQLException e) {

            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }
    }




//    private void responseUser(int id, HttpServletResponse response)
//            throws ServletException, IOException, SQLException {
//        User user = UserService.getInstance().find(id);
//        String user_json = JSON.toJSONString(user);
//        response.getWriter().println(user_json);
//    }
    private void responseDepartmentsByUsername(String username, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        User users = UserService.getInstance().findByUsername(username);
        String user_json = JSON.toJSONString(users);
        response.getWriter().println(user_json);
    }
    private void responseUsers(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Collection<User> users = UserService.getInstance().findAll();
        String users_json = JSON.toJSONString(users);

        response.getWriter().println(users_json);
    }
}
