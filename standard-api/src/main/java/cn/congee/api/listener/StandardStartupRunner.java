package cn.congee.api.listener;

import cn.congee.api.common.constant.ResponseCodeConst;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午2:54
 **/
@Component
public class StandardStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        ResponseCodeConst.init();
    }
}
