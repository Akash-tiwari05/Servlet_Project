package test;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;



@WebFilter(urlPatterns = "/*")
public class FirstFilter implements Filter  {

    
    public FirstFilter() {
   
    }

	
	public void init(ServletConfig config) throws ServletException {
		 
	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request =(HttpServletRequest)req;  
		HttpServletResponse response =(HttpServletResponse)res;
		
		String apiKey =request.getHeader(null);
		if("123akash".equals(apiKey)) {
			chain.doFilter(req, res);
			
		}else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Requst is not valid :");
		}
	}

}
