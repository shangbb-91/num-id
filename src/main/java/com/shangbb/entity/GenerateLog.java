package com.shangbb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @Author benben.shang
 * @Date 2021/4/9 16:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateLog {
    @Id
    private Long id;

    private Integer used;

    private Long useTime;
}
