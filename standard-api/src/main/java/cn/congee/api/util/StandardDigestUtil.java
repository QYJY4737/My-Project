package cn.congee.api.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:56
 **/
public class StandardDigestUtil extends DigestUtils {

    /**
     * md5加盐加密
     *
     * @param salt
     * @param password
     * @return
     */
    public static String encryptPassword(String salt, String password) {
        return StandardDigestUtil.md5Hex(String.format(salt, password));
    }

}
