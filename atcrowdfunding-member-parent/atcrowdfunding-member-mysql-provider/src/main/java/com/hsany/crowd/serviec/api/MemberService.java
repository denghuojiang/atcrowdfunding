package com.hsany.crowd.serviec.api;

import com.hsnay.crowd.entity.po.MemberPO;

public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}
