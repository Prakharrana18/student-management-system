package StudentManagement.StudentManagement.security;

import StudentManagement.StudentManagement.entity.User;
import StudentManagement.StudentManagement.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final   AuthUtil authUtil;
  private final   UserRepository userRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

           final String headerToken = request.getHeader("Authorization");
            if(headerToken==null||!headerToken.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            String token = headerToken.split("Bearer ")[1];
            String username= authUtil.extractUserName(token);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                User user=userRepository.findByUsername(username).orElse(null);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(user,null ,user.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request,response);
            return;
        }catch (Exception ex){

     handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
}
