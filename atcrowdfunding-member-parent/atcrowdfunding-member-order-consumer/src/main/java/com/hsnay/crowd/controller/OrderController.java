package com.hsnay.crowd.controller;

import com.hsnay.crowd.api.MySQLRemoteService;
import com.hsnay.crowd.entity.vo.AddressVO;
import com.hsnay.crowd.entity.vo.MemberLoginVO;
import com.hsnay.crowd.entity.vo.OrderProjectVO;
import com.hsnay.crowd.util.CrowdConstant;
import com.hsnay.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session) {
        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        Integer returnCount = orderProjectVO.getReturnCount();

        return "redirect:http://localhost/order/confirm/order/" + returnCount;
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount,
                                       HttpSession session) {
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO", orderProjectVO);
        MemberLoginVO member = (MemberLoginVO) session.getAttribute("member");
        Integer memberId = member.getId();
        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(memberId);
        System.out.println(resultEntity.getData());
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            List<AddressVO> list = resultEntity.getData();
            session.setAttribute("addressVOList", list);
        }
        return "order-confirm-order";
    }

    @RequestMapping("/confirm/return/info/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("returnId") Integer returnId,
                                        HttpSession httpSession) {
        ResultEntity<OrderProjectVO> orderProjectVOResultEntity = mySQLRemoteService.getOrderProjectVORemote(returnId);
        String result = orderProjectVOResultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {
            OrderProjectVO orderProjectVO = orderProjectVOResultEntity.getData();
            httpSession.setAttribute("orderProjectVO", orderProjectVO);
        }

        return "order-confirm-return";
    }
}
