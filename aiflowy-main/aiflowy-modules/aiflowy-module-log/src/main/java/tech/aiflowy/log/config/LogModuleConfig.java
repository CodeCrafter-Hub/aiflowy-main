package tech.aiflowy.log.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.aiflowy.log.reporter.ActionLogReporterProperties;
import tech.aiflowy.log.reporter.ActionReportInterceptor;

@MapperScan("tech.aiflowy.log.mapper")
@Configuration
public class LogModuleConfig implements WebMvcConfigurer {

    private final ActionLogReporterProperties logProperties;
    private final ActionReportInterceptor actionReportInterceptor;

    public LogModuleConfig(ActionLogReporterProperties logProperties,
                           ActionReportInterceptor actionReportInterceptor) {
        this.logProperties = logProperties;
        this.actionReportInterceptor = actionReportInterceptor;

        System.out.println("启用模块 >>>>>>>>>> module-log");
    }


    /**
     * 注册日志拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (!logProperties.isEnabled()) {
            return;
        }

        registry.addInterceptor(actionReportInterceptor)
                .addPathPatterns(logProperties.getIncludePatterns().toArray(new String[0]))
                .excludePathPatterns(logProperties.getExcludePatterns().toArray(new String[0]));
    }

}
