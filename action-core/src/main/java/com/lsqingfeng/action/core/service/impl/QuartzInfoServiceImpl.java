package com.lsqingfeng.action.core.service.impl;

import com.lsqingfeng.action.core.entity.QuartzInfo;
import com.lsqingfeng.action.core.mapper.QuartzInfoMapper;
import com.lsqingfeng.action.core.service.QuartzInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统定时任务信息表 服务实现类
 * </p>
 *
 * @author lsqingfeng
 * @since 2020-03-10
 */
@Service
public class QuartzInfoServiceImpl extends ServiceImpl<QuartzInfoMapper, QuartzInfo> implements QuartzInfoService {

}
