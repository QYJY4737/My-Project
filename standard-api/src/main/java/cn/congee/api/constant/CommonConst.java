package cn.congee.api.constant;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * [ 通用常量 ]
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:57
 **/
public class CommonConst {

    public static final class Page {
        public static final Integer SIZE = 10;
    }

    public static final class Password {
        public static final String DEFAULT = "123456";
        public static final String SALT_FORMAT = "smart_%s_admin";
    }

    public static final String IGNORE_H5_URL_MAPPING = "/h5/api";

    public static final class CommonCollection {
        public static final Set<String> IGNORE_URL = ImmutableSet.of("/swagger", "Excel");

        public static final Set<String> IGNORE_URL_MAPPING = ImmutableSet.of(IGNORE_H5_URL_MAPPING);

        public static Boolean contain(Set<String> ignores, String uri) {
            if (CollectionUtils.isEmpty(ignores)) {
                return false;
            }
            for (String ignoreUrl : ignores) {
                if (uri.startsWith(ignoreUrl)) {
                    return true;
                }
            }
            return false;
        }
    }

}
