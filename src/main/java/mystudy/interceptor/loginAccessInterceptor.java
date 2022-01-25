package mystudy.interceptor;

import mystudy.po.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /**
 *  * 非法访问拦截
 *  * --拦截所有资源：
 *  * ----所有的资源   /*
 *  * <p>
 *  * ----需要被放行的资源：
 *  * ------1、指定页面，放行（用户无需登录即可访问的页面；例如：登录页面login.jsp、注册页面register.jsp
 *  * ------2、静态资源，放行（存放在statics目录下的资源；例如：js、css、images等）
 *  * ------3、指定行为，放行（用户无需登录即可执行的操作；例如：登录操作actionName=login等)
 *  * ------4、登录状态，放行（判断session作用域中是否存在user对象；存在则放行，不存在，则拦截跳转到登录页面）
 *  * --
 *  * 免登陆（自动登录）
 *  * --通过Cookie对象实现
 *  * --
 *  * --什么时候需要免登陆：
 *  * ----当用户处于未登录状态，且去请求需要登录才能访问的资源时，调用自动登录功能
 *  * --
 *  * ----目的：让用户处于登录状态（自动调用免登录方法）
 *  * --
 *  * ----实现：
 *  * ------从Cookie对象中获取用户的姓名与密码，自动执行登录操作
 *  * --------1、获取Cookie数据， request.getCookies()
 *  * --------2、判断Cookie数组
 *  * --------3、遍历Cookie数组，获取指定的Cookie对象（name为user的Cookie对象）
 *  * --------4、得到对应的cookie对象的value（姓名与密码：userName-userPwd)
 *  * --------5、通过split()方法将value字符串分割成数组
 *  * --------6、从数组中分别得到对应的姓名与密码值
 *  * --------7、请求转发到登录操作 /user?actionName=login&userName=姓名&userPwd=密码
 *  * --------8、return
 *  * <p>
 *  * --如果以上判断都不满足，则拦截跳转到登录页面
 *
 */
public class loginAccessInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

		String path = request.getRequestURI();
		if (path.contains("/login.jsp")) {
			// return true;
		}
		if (path.contains("/static")) {
			return true;
		}
		if (path.contains("/user/login")) {
			return true;
		}
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			return true;
		}
		//自动登录
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("user".equals(cookie.getName())) {
					String value = cookie.getValue();
					String[] val = value.split("-");
					String userName = val[0];
					String password = val[1];
					String url = request.getContextPath() + "/user/login?rem=1&userName=" + userName + "&userPwd=" + password;
					response.sendRedirect(url);
					return false;
				}
			}
		}

		try {
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return false;
	}
}
