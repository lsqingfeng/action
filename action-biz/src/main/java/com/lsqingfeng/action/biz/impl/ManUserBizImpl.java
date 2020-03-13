package com.lsqingfeng.action.biz.impl;

import com.lsqingfeng.action.biz.ManUserBiz;
import com.lsqingfeng.action.core.entity.ManUser;
import com.lsqingfeng.action.core.service.ManUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

@Service
public class ManUserBizImpl implements ManUserBiz {

    @Autowired
    private ManUserService manUserService;


    @Override
    @Cacheable(cacheNames = "man_user", key = "#id")
    public ManUser getById(Long id) {
        return manUserService.getById(id);
    }

    @Override
    public boolean saveManUser(ManUser manUser) {
        return manUserService.save(manUser);
    }

    @Override
    @CachePut(cacheNames="man_user", key="#newObj.id")
    public boolean updateManUser(ManUser manUser) {
        return manUserService.updateById(manUser);
    }

    @Override
    @CacheEvict(cacheNames = "man_user", key = "#id")
    public boolean deleteManUser(Long id) {
        return manUserService.removeById(id);
    }
}
