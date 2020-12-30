package cn.congee.api.module.support.idgenerator.domain;

import java.util.Date;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:39
 **/
public class IdGeneratorLastNumberDTO {

    private Date updateTime;
    private Long lastNumber;
    private Date databaseTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Long lastNumber) {
        this.lastNumber = lastNumber;
    }

    public Date getDatabaseTime() {
        return databaseTime;
    }

    public void setDatabaseTime(Date databaseTime) {
        this.databaseTime = databaseTime;
    }

}
