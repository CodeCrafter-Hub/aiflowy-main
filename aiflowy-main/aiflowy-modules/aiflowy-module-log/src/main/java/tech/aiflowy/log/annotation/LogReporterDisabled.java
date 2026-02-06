package tech.aiflowy.log.annotation;


import java.lang.annotation.*;

/**
 * 标记后，LogReporterDisabled 将跳过此方法或类的日志输出
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogReporterDisabled {
}