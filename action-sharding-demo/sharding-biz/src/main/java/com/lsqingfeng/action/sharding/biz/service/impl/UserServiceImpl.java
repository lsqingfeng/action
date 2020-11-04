package com.lsqingfeng.action.sharding.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsqingfeng.action.sharding.biz.entity.User;
import com.lsqingfeng.action.sharding.biz.mapper.UserMapper;
import com.lsqingfeng.action.sharding.biz.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
