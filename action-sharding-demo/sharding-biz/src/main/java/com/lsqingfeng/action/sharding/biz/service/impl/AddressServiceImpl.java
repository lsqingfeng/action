package com.lsqingfeng.action.sharding.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsqingfeng.action.sharding.biz.entity.Address;
import com.lsqingfeng.action.sharding.biz.mapper.AddressMapper;
import com.lsqingfeng.action.sharding.biz.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liushuai
 * @since 2020-09-03
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
