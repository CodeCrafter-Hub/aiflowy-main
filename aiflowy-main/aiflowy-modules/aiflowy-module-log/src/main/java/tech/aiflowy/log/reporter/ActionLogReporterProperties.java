package tech.aiflowy.log.reporter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "aiflowy.log.reporter")
public class ActionLogReporterProperties {

    /**
     * 是否启用 Action 报告
     */
    private boolean enabled = true;

    /**
     * 采样率（0.0 ~ 1.0），1.0 = 100%
     */
    private double sampleRate = 1.0;

    /**
     * 包含的路径模式
     */
    private List<String> includePatterns = Arrays.asList("/**");

    /**
     * 排除的路径模式
     */
    private List<String> excludePatterns = Arrays.asList(
            "/static/**",
            "/assets/**",
            "/js/**",
            "/css/**",
            "/images/**",
            "/favicon.ico",
            "/actuator/**",
            "*.js",
            "*.css",
            "*.png",
            "*.jpg",
            "*.gif",
            "*.ico"
    );


    // getter and setter
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(double sampleRate) {
        this.sampleRate = sampleRate;
    }

    public List<String> getIncludePatterns() {
        return includePatterns;
    }

    public void setIncludePatterns(List<String> includePatterns) {
        this.includePatterns = includePatterns;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }
}