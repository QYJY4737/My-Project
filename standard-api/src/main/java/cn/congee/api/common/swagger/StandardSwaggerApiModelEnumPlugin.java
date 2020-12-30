package cn.congee.api.common.swagger;

import cn.congee.api.common.anno.ApiModelPropertyEnum;
import cn.congee.api.common.domain.BaseEnum;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.lang.reflect.AnnotatedElement;

import static springfox.documentation.schema.Annotations.findPropertyAnnotation;

/**
 * swagger 用于说明枚举类字段说明
 * SWAGGER_PLUGIN_ORDER+1 是将此配置放在原来的后面执行
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午12:19
 **/
public class StandardSwaggerApiModelEnumPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelPropertyEnum> enumOptional = Optional.absent();

        if (context.getAnnotatedElement().isPresent()) {
            enumOptional = enumOptional.or(findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            enumOptional = enumOptional.or(findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelPropertyEnum.class));
        }

        if (enumOptional.isPresent()) {
            ApiModelPropertyEnum anEnum = enumOptional.get();
            String enumInfo = BaseEnum.getInfo(anEnum.value());
            context.getBuilder()
                    .required(enumOptional.transform(toIsRequired()).or(false))
                    .description(anEnum.enumDesc() + ":" + enumInfo)
                    .example(enumOptional.transform(toExample()).orNull())
                    .isHidden(anEnum.hidden());
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }

    static Function<ApiModelPropertyEnum, Boolean> toIsRequired() {
        return annotation -> annotation.required();
    }

    public static Optional<ApiModelPropertyEnum> findApiModePropertyAnnotation(AnnotatedElement annotated) {
        return Optional.fromNullable(AnnotationUtils.getAnnotation(annotated, ApiModelPropertyEnum.class));
    }

    static Function<ApiModelPropertyEnum, String> toExample() {
        return annotation -> {
            String example = annotation.example();
            if (StringUtils.isBlank(example)) {
                return "";
            }
            return example;
        };
    }

}
