package hn.uth.sistemadetransporte.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String requestUri = httpRequest.getRequestURI();

        boolean esPaginaLogin = requestUri.equals(contextPath + "/index.xhtml") || requestUri.equals(contextPath + "/");
        boolean esRecursoJSF = requestUri.contains("/jakarta.faces.resource/")
                || requestUri.contains("/javax.faces.resource/");
        boolean esPublico = esPaginaLogin || esRecursoJSF;

        if (esPublico) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        boolean autenticado = session != null && session.getAttribute("usuarioAutenticado") != null;

        if (!autenticado) {
            redirigirALogin(httpRequest, httpResponse);
            return;
        }

        aplicarNoCache(httpResponse);
        chain.doFilter(request, response);
    }

    private void redirigirALogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String loginUrl = request.getContextPath() + "/index.xhtml";
        aplicarNoCache(response);

        if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
            response.setContentType("text/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<?xml version='1.0' encoding='UTF-8'?><partial-response><redirect url='"
                    + loginUrl + "'/></partial-response>");
        } else {
            response.sendRedirect(loginUrl);
        }
    }

    private void aplicarNoCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }
}