package tech.aiflowy.common.web.error;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.View;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JakartaJsonView implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (model != null) {
            String jsonString = JSON.toJSONString(model);
            try (ServletOutputStream out = response.getOutputStream()) {
                out.write(jsonString.getBytes(StandardCharsets.UTF_8));
                out.flush();
            }
        }
    }
}
