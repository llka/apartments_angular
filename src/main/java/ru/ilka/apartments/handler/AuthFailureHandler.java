package ru.ilka.apartments.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static Logger logger = LogManager.getLogger(AuthFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        if (exception.getClass().equals(DisabledException.class)) {
            getRedirectStrategy().sendRedirect(request, response, "/login?ban=true");
        } else if (exception.getClass().equals(BadCredentialsException.class)) {
            getRedirectStrategy().sendRedirect(request, response, "/login?error=true");
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
