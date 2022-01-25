package mystudy.controller;

import mystudy.po.User;
import mystudy.service.UserService;
import mystudy.vo.ResultInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/user")
@MultipartConfig
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	private String userLogin(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("menu_page", "user");
		ResultInfo<User> resultInfo = userService.userLogin(user);

		// 判断是否登录成功
		if (resultInfo.getCode() == 1) {
			request.getSession().setAttribute("user", resultInfo.getResult());
			request.setAttribute("test", resultInfo.getResult());
			System.out.println(resultInfo.getResult());
			// 是否记住密码，如果是，rem为1
			String rem = request.getParameter("rem");
			if ("1".equals(rem)) {
				Cookie cookie = new Cookie("user", user.getUserName() + "-" + user.getUserPwd());
				cookie.setMaxAge(3 * 24 * 60 * 60);
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
			} else {
				Cookie cookie = new Cookie("user", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}

			return "redirect:/index/searchAll";	//redirect是另一次请求，不共享request域中的对象，forward会共享请求域中的对象
		} else {
			request.setAttribute("resultInfo",resultInfo);
			return "WEB-INF/login";
		}
	}

	@RequestMapping("/logout")
	private String userLogOut(HttpServletRequest request, HttpServletResponse response) {
		// 销毁session对象
		request.getSession().invalidate();
		Cookie cookie = new Cookie("user", null);
		cookie.setMaxAge(0);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
		return "redirect:/login";
	}

	@RequestMapping("/userCenter")
	private String userCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("menu_page", "user");
		String updateUserActionName = request.getParameter("updateUserActionName");
		if ("updateUser".equals(updateUserActionName)) {
			ResultInfo<User> resultInfo = userService.updateUser(request);
			request.setAttribute("resultInfo", resultInfo);
		}

		// 1.设置首页动态包含的页面值
		request.setAttribute("changePage", "/user/info.jsp"); // 设置需要的参数后再次请求转发到目标页面

		// 2.请求转发跳转到index.jsp
		return "WEB-INF/index";
	}

	@RequestMapping("/userHead")
	private void userHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("menu_page", "user");
		String imageName = request.getParameter("imageName");
		String realPath = request.getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(realPath + "/" + imageName);
		//获得文件的MIME类型
		String mimeType = request.getServletContext().getMimeType(realPath + "/" + imageName);
		response.setContentType(mimeType);
		FileUtils.copyFile(file, response.getOutputStream());
	}

	@RequestMapping("/checkNick")
	@ResponseBody
	private String checkNick( HttpServletRequest request, String nick) throws IOException {
		request.setAttribute("menu_page", "user");
		User user = (User) request.getSession().getAttribute("user");
		Integer code = userService.checkNick(nick, user.getUserId());
		return code + "";
		// response.getWriter().write(code + "");	//code需要转为字符串去响应
		// response.getWriter().close();
	}

}
