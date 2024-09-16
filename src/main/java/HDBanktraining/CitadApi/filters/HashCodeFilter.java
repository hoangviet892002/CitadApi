package HDBanktraining.CitadApi.filters;

import HDBanktraining.CitadApi.controllers.testApi.Get.GetTestApi;
import HDBanktraining.CitadApi.utils.HashCodeUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

@Component
@RequiredArgsConstructor
public class HashCodeFilter extends OncePerRequestFilter {

    private final HashCodeUtil hashCodeUtil;
    private static final Logger logger = Logger.getLogger(HashCodeFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final List<Pair<String, String>> bypassToken = Arrays.asList(
                Pair.of("/swagger-ui/index.html", "GET"),
                Pair.of("/v3/api-docs", "GET"),
                Pair.of("/swagger-ui/swagger-initializer.js", "GET")
        );

        for (Pair<String, String> bypasstoken : bypassToken) {
            if (request.getServletPath().contains(bypasstoken.getFirst())) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
//            String key = request.getHeader("Api-Key");
            String key = "HashCodeKey";

            if (key == null) {
                logger.info("No valid Key provided");
                throw new ServletException("No valid Key provided");
            }
            if (!hashCodeUtil.verifyHashCode(key)) {
                logger.info("Invalid Key");
                throw new ServletException("Invalid Key");
            }
        } catch (Exception e) {
            logger.info("Exception Invalid Key!!");
            throw new ServletException("Invalid Key");
        }
        filterChain.doFilter(request, response);

    }
}
