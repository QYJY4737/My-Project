package cn.congee.api.module.system.login.domain;

import cn.congee.api.module.system.employee.domain.bo.EmployeeBO;
import lombok.Getter;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午4:54
 **/
@Getter
public class RequestTokenBO {

    private Long requestUserId;

    private EmployeeBO employeeBO;

    public RequestTokenBO(EmployeeBO employeeBO) {
        this.requestUserId = employeeBO.getId();
        this.employeeBO = employeeBO;
    }

    @Override
    public String toString() {
        return "RequestTokenBO{" +
                "requestUserId=" + requestUserId +
                ", employeeBO=" + employeeBO +
                '}';
    }

}
