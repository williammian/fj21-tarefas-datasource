package br.com.caelum.tarefas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle Autorizador");
				
		String uri = request.getRequestURI();
		
		if(uri.endsWith("loginForm") || uri.endsWith("efetuaLogin") || uri.contains("resources")){
			return true;
		}
		
		if(request.getSession().getAttribute("usuarioLogado") != null){
			return true;
		}
		
		response.sendRedirect("loginForm");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		System.out.println("postHandle Autorizador");
	}
	
}
