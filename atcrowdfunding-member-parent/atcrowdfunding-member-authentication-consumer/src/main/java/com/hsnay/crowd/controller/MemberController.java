package com.hsnay.crowd.controller;

import com.hsnay.crowd.api.MySQLRemoteService;
import com.hsnay.crowd.api.RedisRemoteService;
import com.hsnay.crowd.config.ShortMessageProperties;
import com.hsnay.crowd.entity.po.MemberPO;
import com.hsnay.crowd.entity.vo.MemberLoginVO;
import com.hsnay.crowd.entity.vo.MemberVo;
import com.hsnay.crowd.util.CrowdConstant;
import com.hsnay.crowd.util.CrowdUtil;
import com.hsnay.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.beans.Beans;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberController {

    @Autowired
    private ShortMessageProperties shortMessageProperties;
    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;


    @RequestMapping("/auth/do/member/logout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/auth/do/member/login")
    public String doLogin(@RequestParam("loginacct") String loginacct,
                          @RequestParam("loginpswd") String userpwsd,
                          ModelMap modelMap,
                          HttpSession session) {
        ResultEntity<MemberPO> memberPOByLoginAcctRemote = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if (memberPOByLoginAcctRemote.getResult().equals("FAILED")) {
            modelMap.addAttribute("message", memberPOByLoginAcctRemote.getMessage());
            return "member-login";
        }
        MemberPO memberPO = memberPOByLoginAcctRemote.getData();
        if (memberPO == null) {
            modelMap.addAttribute("message", "用户名不存在");
            return "member-login";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(userpwsd, memberPO.getUserpswd());
        if(!matches){
            modelMap.addAttribute("message", CrowdConstant.MESSAGE_LOGIN_IN_FAILED);
            return "member-login";
        }
        System.out.println(memberPO);
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        System.out.println(memberLoginVO);
        session.setAttribute("member",memberLoginVO);
        return "redirect:http://localhost/auth/member/to/center";

    }

    @RequestMapping("/auth/member/do/register")
    public String register(MemberVo memberVo, ModelMap modelMap) {
        System.out.println(memberVo);

        String phoneNum = memberVo.getPhoneNum();
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        ResultEntity<String> redisStringValueByKey = redisRemoteService.getRedisStringValueByKey(key);
        String result = redisStringValueByKey.getResult();
        if (result.equals("FAILED")) {
            modelMap.addAttribute("message", "验证码已过期");
            return "member-reg";
        }
        if (result.equals("SUCCESS")) {
            String code = redisStringValueByKey.getData();
            if (code == null) {
                modelMap.addAttribute("message", "验证码已过期");
                return "member-reg";
            }
            String formCode = memberVo.getCode();
            if (code != null) {
                if (code.equals(formCode)) {
                    //成功将验证码从redis中删除
                    redisRemoteService.removeRedisKeyRemote(key);
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String userPswd = memberVo.getUserpswd();
                    String encode = passwordEncoder.encode(userPswd);
                    //转换成po对象
                    memberVo.setUserpswd(encode);
                    //复制对象
                    MemberPO memberPO = new MemberPO();
                    BeanUtils.copyProperties(memberVo, memberPO);
                    //调用远程方法
                    ResultEntity<String> saveMember = mySQLRemoteService.saveMember(memberPO);
                    if (saveMember.getResult().equals("FAILED")) {
                        modelMap.addAttribute("message", saveMember.getMessage());
                        return "member-reg";
                    }
                } else {
                    modelMap.addAttribute("message", "验证码不正确");
                    return "member-reg";
                }
            }

        }
        return "redirect:/auth/member/to/login/page";
    }


    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        ResultEntity<String> result = CrowdUtil.sendShortMessage(shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                phoneNum,
                shortMessageProperties.getAppCode());
        String success = result.getResult();
        if (success.equals("SUCCESS")) {
            String code = result.getData();
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            long TTL = 15l;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, TTL, TimeUnit.MINUTES);
            if (saveCodeResultEntity.getResult().equals("SUCCESS")) {
                return ResultEntity.successWithoutData();
            } else {
                return saveCodeResultEntity;
            }
        }

        return null;
    }
}
