package com.hsany.crowd.serviec.impl;

import com.hsany.crowd.mapper.MemberPOMapper;
import com.hsany.crowd.serviec.api.MemberService;
import com.hsnay.crowd.entity.po.MemberPO;
import com.hsnay.crowd.entity.po.MemberPOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample example = new MemberPOExample();
        MemberPOExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
