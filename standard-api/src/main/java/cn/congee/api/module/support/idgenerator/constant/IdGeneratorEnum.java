package cn.congee.api.module.support.idgenerator.constant;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:40
 **/
public enum IdGeneratorEnum {

    /**
     * 测试generate
     */
    TEST_ID_GENERATOR(2, "testIdGenerator"),

    ORDER(1, "order");

    private long id;
    private String keyName;

    IdGeneratorEnum(int id, String keyName) {
        this.id = id;
        this.keyName = keyName;
    }

    @Override
    public String toString() {
        return "IdGeneratorEnum{" + "id=" + id + ", keyName='" + keyName + '\'' + '}';
    }

    public long getId() {
        return id;
    }

    public String getKeyName() {
        return keyName;
    }

}
