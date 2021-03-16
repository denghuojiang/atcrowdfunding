package com.hsany.crowd.serviec.impl;

import com.hsany.crowd.mapper.AddressMapper;
import com.hsany.crowd.mapper.OrderMapper;
import com.hsany.crowd.mapper.OrderProjectMapper;
import com.hsany.crowd.serviec.api.OrderProjectService;
import com.hsnay.crowd.entity.po.Address;
import com.hsnay.crowd.entity.po.AddressExample;
import com.hsnay.crowd.entity.po.Order;
import com.hsnay.crowd.entity.po.OrderProject;
import com.hsnay.crowd.entity.vo.AddressVO;
import com.hsnay.crowd.entity.vo.OrderProjectVO;
import com.hsnay.crowd.entity.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderProjectServiceImpl implements OrderProjectService {
    @Autowired
    private OrderProjectMapper orderProjectMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {
        return orderProjectMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        ArrayList<AddressVO> addressVOList = new ArrayList<>();
        for (Address addressPO : addresses) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveAddress(AddressVO addressVO) {
        Address addressPO = new Address();
        BeanUtils.copyProperties(addressVO, addressPO);
        addressMapper.insertSelective(addressPO);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveOrder(OrderVO orderVO) {

        Order orderPO = new Order();
        BeanUtils.copyProperties(orderVO, orderPO);
        OrderProjectVO orderProjectVO = orderVO.getOrderProjectVO();
        OrderProject orderProjectPO = new OrderProject();
        BeanUtils.copyProperties(orderProjectVO, orderProjectPO);
        orderMapper.insertSelective(orderPO);
        Integer id = orderPO.getId();
        orderProjectPO.setOrderId(id);
        orderProjectMapper.insertSelective(orderProjectPO);
    }
}
