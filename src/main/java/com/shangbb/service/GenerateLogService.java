package com.shangbb.service;

import com.shangbb.entity.GenerateLog;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author benben.shang
 * @Date 2021/4/9 17:02
 */
@Service
public class GenerateLogService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RegisterService registerService;

    public List<GenerateLog> listByIds(List<Long> ids, Integer seq, Integer used) {
        Criteria criteria = Criteria.where("_id").in(ids);
        if (Objects.nonNull(used)) {
            criteria.and("used").is(used);
        }
        return mongoTemplate.find(new Query(criteria), GenerateLog.class, "sidmgrGenerateLog" + seq);
    }

    public void saveAll(List<Long> ids, Integer seq) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<GenerateLog> collect = ids.stream().map(id -> GenerateLog.builder().id(id).used(0).build()).collect(Collectors.toList());
        mongoTemplate.insert(collect, "sidmgrGenerateLog" + seq);
    }

    public List<GenerateLog> loadUnused(Integer seq) {
        Criteria criteria = Criteria.where("used").is(0);
        return mongoTemplate.find(new Query(criteria), GenerateLog.class, "sidmgrGenerateLog" + seq);
    }

    public <T extends Number> void setUsedByIds(String registerId, @Nonnull List<T> ids) {
        Integer seq = registerService.getSeq(registerId);
        Criteria criteria = Criteria.where("_id").in(ids);
        Update update = new Update().set("used", 1).set("useTime", System.currentTimeMillis());
        mongoTemplate.updateMulti(new Query(criteria), update, "sidmgrGenerateLog" + seq);
    }
}
