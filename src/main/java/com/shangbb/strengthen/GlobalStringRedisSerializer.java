package com.shangbb.strengthen;

import com.shangbb.config.BaseAppConfig;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * @Author benben.shang
 * @Date 2021/2/8 14:44
 */
@AllArgsConstructor
public class GlobalStringRedisSerializer extends StringRedisSerializer {
    private final BaseAppConfig baseAppConfig;

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        String deserialize = super.deserialize(bytes);
        if (Objects.isNull(deserialize) || !deserialize.contains(baseAppConfig.getRedisKeyPrefix())) {
            return null;
        }
        return deserialize.replace(baseAppConfig.getRedisKeyPrefix(), "");
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        string = baseAppConfig.getRedisKeyPrefix() + string;
        return super.serialize(string);
    }
}
