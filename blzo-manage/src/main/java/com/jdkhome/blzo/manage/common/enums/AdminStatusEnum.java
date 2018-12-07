package com.jdkhome.blzo.manage.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: wxb
 * CreatedTime: 2018/6/22 下午4:46
 * Description:订单状态枚举
 */
public enum AdminStatusEnum {

    INIT(0, "初始"),
    NORMAL(1, "正常"),
    FREEZE(-1, "冻结");

    Integer code;
    String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    AdminStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AdminStatusEnum getByCode(Integer code) {

        //1 get all the enum constants
        AdminStatusEnum[] enumTypes = values();

        //2 judge if constants contains name
        for (AdminStatusEnum enumType : enumTypes) {
            if (enumType.getCode().equals(code)) {
                return enumType;
            }
        }
        return null;
    }
}
