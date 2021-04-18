package com.virgo.spring.security.resoure.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * 用户信息实体
 * @author HZI.HUI
 * @version 1.0
 * @since 2021/4/17
 */
@Data
@Accessors(chain = true)
public class UserEntity {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;
}
