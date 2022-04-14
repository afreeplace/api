package seyfa.afreeplace.filters;

import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.security.CustomUserDetailsService;
import seyfa.afreeplace.services.jwt.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // --------------------------------------------------

    @Autowired
    UserRequest userRequest;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");

        String authToken = null;
        String email     = null;

        // validate or not authorization
        if (authorization != null && authorization.startsWith("Bearer ")) {
            authToken = authorization.split(" ")[1];
            email = jwtManager.extractUsername(authToken);
            // extract authToken from authorization
            // extract phone from authToken
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // find user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                // Validate token
                // Validate authentification so SS
                // will open end-points
                if (jwtManager.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    // valideer la connexion pour le filtre
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    // Load user for the current request
                    userRequest.setAuthUser(userRepository.findByEmail(email).get());
                }
            }
        }

        // Dans tout les cas oon continue
        // si la co n'a pas été validé, le requête ne passera
        // pas avec spring security, a part si le chemin
        // demandé est autorisé
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }
}
