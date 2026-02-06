package tech.aiflowy.log.reporter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * 响应缓存 Filter，支持基于路径的排除规则
 */
@Component
@Order(HIGHEST_PRECEDENCE)
@ConditionalOnProperty(
        prefix = "aiflowy.log.reporter",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true  // 默认开启
)
public class ResponseCachingFilter implements Filter {

    @Autowired
    private ActionLogReporterProperties logProperties;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // 1如果是 OPTIONS 请求，跳过（通常为预检）
        if ("OPTIONS".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }

//        // 检查是否为 SSE 请求
//        if (isSseRequest(httpRequest)) {
//            chain.doFilter(request, response);
//            return;
//        }

        //  检查是否匹配排除路径
        if (isExcluded(uri)) {
            chain.doFilter(request, response);
            return;
        }

        //  检查是否匹配包含路径（一般为 /**，可省略）
        if (!isIncluded(uri)) {
            chain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
        if (isSseRequest(httpRequest)) {
            // SSE 请求不缓存
            chain.doFilter(requestWrapper, response);
            return;
        }


        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            responseWrapper.copyBodyToResponse(); // 必须调用
        }
    }

    private boolean isExcluded(String uri) {
        return logProperties.getExcludePatterns().stream().anyMatch(p -> match(uri, p));
    }

    private boolean isIncluded(String uri) {
        return logProperties.getIncludePatterns().stream().anyMatch(p -> match(uri, p));
    }

    /**
     * 判断是否为 SSE 请求（基于标准 Accept 头）
     */
    private boolean isSseRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return accept != null && accept.contains("text/event-stream");
    }

    /**
     * 简单的路径匹配（支持 * 和 **）
     * 注意：这里简化实现，生产可替换为 AntPathMatcher
     */
    private boolean match(String path, String pattern) {
        if (pattern.equals("/**")) {
            return true;
        }
        if (pattern.endsWith("/**")) {
            String prefix = pattern.substring(0, pattern.length() - 3);
            return path.startsWith(prefix);
        }
        if (pattern.endsWith("*")) {
            String prefix = pattern.substring(0, pattern.length() - 1);
            return path.startsWith(prefix);
        }
        if (pattern.contains("*") && !pattern.contains("/**")) {
            // 支持 *.js, *.css
            String p = pattern.replace("*", "").replace(".", "\\.");
            return path.matches(".*" + p + ".*");
        }
        return path.equals(pattern);
    }
}