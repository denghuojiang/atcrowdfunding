package com.hsnay.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberVo implements Serializable {
    private String loginacct;
    private String userpswd;
    private String username;
    private String email;
    private String phoneNum;
    private String code;
}
