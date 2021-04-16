package com.shangbb.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author benben.shang
 * @Date 2021/4/9 15:46
 */
@Data
@Document(collection = "sidmgrRegister")
public class Register {
    @Id
    private ObjectId id;
    /**
     * 注册code
     */
    private String code;
    /**
     * 最小长度
     */
    private Integer minLength;
    /**
     * 最大长度
     */
    private Integer maxLength;
    /**
     * 最小值
     */
    private Long minVal;
    /**
     * 最大值
     */
    private Long maxVal;
    /**
     * 队列缓存长度
     */
    private Integer cacheLength = 100;

    private Integer seq;

    /**
     * 是否删除
     */
    private Integer dr;
}
