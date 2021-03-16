package com.hsany.crowd.serviec.api;

import com.hsnay.crowd.entity.vo.AddressVO;
import com.hsnay.crowd.entity.vo.OrderProjectVO;
import com.hsnay.crowd.entity.vo.OrderVO;

import java.util.List;

public interface OrderProjectService {
    OrderProjectVO getOrderProjectVO(Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}
