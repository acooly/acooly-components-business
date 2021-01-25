package com.acooly.module.member.service.busitype;

import com.acooly.module.member.MemberBusiType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-12 15:47
 */
@Getter
@Setter
public class DefaultMemberBusiType implements MemberBusiType {

    private String code;

    private String name;

    public DefaultMemberBusiType() {
    }

    public DefaultMemberBusiType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultMemberBusiType that = (DefaultMemberBusiType) o;
        return getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", getCode())
                .append("name", getName())
                .toString();
    }
}
