package name.martingeisse.electronics_board.backend.platform;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CorsFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getHeader("Origin") != null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
                response.addHeader("Access-Control-Allow-Methods", "OPTIONS, HEAD, GET, POST, PUT, DELETE");
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
                response.addHeader("Access-Control-Allow-Credentials", "true");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        }
        chain.doFilter(request, response);
    }

}
