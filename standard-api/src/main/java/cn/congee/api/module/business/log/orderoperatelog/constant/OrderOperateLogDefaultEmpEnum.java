package cn.congee.api.module.business.log.orderoperatelog.constant;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午8:38
 **/
public enum OrderOperateLogDefaultEmpEnum {

    DEFAULT_EMP(0,"系统");

    private Integer empId;

    private String empName;

    OrderOperateLogDefaultEmpEnum(Integer empId,String empName) {
        this.empId = empId;
        this.empName = empName;
    }

    public int getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

}
