package com.lsqingfeng.action.core.service.impl;

import com.lsqingfeng.action.core.entity.ManUser;
import com.lsqingfeng.action.core.mapper.ManUserMapper;
import com.lsqingfeng.action.core.service.ManUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理系统用户表 服务实现类
 * </p>
 *
 * @author lsqingfeng
 * @since 2020-03-10
 */
@Service
public class ManUserServiceImpl extends ServiceImpl<ManUserMapper, ManUser> implements ManUserService {

}
