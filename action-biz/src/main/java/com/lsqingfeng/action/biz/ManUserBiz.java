package com.lsqingfeng.action.biz;

import com.lsqingfeng.action.core.entity.ManUser;

public interface ManUserBiz {

    ManUser getById(Long id);

    boolean saveManUser(ManUser manUser);

    boolean updateManUser(ManUser manUser);

    boolean deleteManUser(Long id);

}
