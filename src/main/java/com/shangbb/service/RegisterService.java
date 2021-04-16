package com.shangbb.service;

import com.shangbb.entity.Register;
import com.shangbb.manager.QueueManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author benben.shang
 * @Date 2021/4/9 15:55
 */
@Service
public class RegisterService {

    @Resource
    private QueueManager queueManager;

    @Resource
    private MongoTemplate mongoTemplate;
    private Map<String, Integer> seqMap = new HashMap<>();

    public void register(Register register) {
        long min = queueManager.getMin(register.getMinLength());
        long max = queueManager.getMax(register.getMaxLength());
        register.setMinVal(min);
        register.setMaxVal(max);
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "_id")).limit(1);
        int seq = 1;
        Register maxOne = mongoTemplate.findOne(query, Register.class);
        if (Objects.nonNull(maxOne)) {
            seq = maxOne.getSeq() + 1;
        }
        register.setSeq(seq);
        register.setDr(0);
        mongoTemplate.save(register);
    }

    public Register getById(String id) {
        Criteria criteria = Criteria.where("_id").is(id);
        return mongoTemplate.findOne(new Query(criteria), Register.class);
    }

    public Integer getSeq(String id) {
        if (seqMap.containsKey(id)) {
            return seqMap.get(id);
        }
        Register register = getById(id);
        Integer seq = register.getSeq();
        seqMap.put(id, seq);
        return seq;
    }

    public List<Register> list() {
        Criteria criteria = Criteria.where("dr").is(0);
        return mongoTemplate.find(new Query(criteria), Register.class);
    }


}
