package tech.aiflowy.common.web.error;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.aiflowy.common.domain.Result;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.ConstraintViolationException;
import tech.aiflowy.common.web.exceptions.BusinessException;

public class GlobalErrorResolver implements HandlerExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        Result<?> error;
        if (ex instanceof MissingServletRequestParameterException) {
            error = Result.fail(1, ((MissingServletRequestParameterException) ex).getParameterName() + " 不能为空.");
        } else if (ex instanceof NotLoginException) {
            response.setStatus(401);
            error = Result.fail(401, "请登录");
        } else if (ex instanceof NotPermissionException || ex instanceof NotRoleException) {
            error = Result.fail(4010, "无权操作");
        } else if (ex instanceof ConstraintViolationException) {
            error = Result.fail(400, ex.getMessage());
        } else if (ex instanceof BusinessException) {
            error = Result.fail(1, ex.getMessage());
        } else {
            LOG.error(ex.toString(), ex);
            error = Result.fail(1, "错误信息：" + ex.getMessage());
        }
        JSONObject object = JSON.parseObject(JSON.toJSONString(error));
        return new ModelAndView(new JakartaJsonView())
                .addAllObjects(object);
    }
}
