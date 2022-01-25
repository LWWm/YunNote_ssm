package mystudy.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class jspFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		if ((request.getContextPath() + "/").equals(request.getRequestURI())) {
			request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, servletResponse);
		}
	}

	@Override
	public void destroy() {

	}
}
