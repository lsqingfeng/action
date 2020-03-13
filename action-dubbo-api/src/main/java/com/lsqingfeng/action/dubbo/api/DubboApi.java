package com.lsqingfeng.action.dubbo.api;

/**
 * dubbo api： consumer和provider 都需要依赖api
 *      provider:  负责实现该接口
 *      consumer:  负责调用该接口
 *
 *      cosumer依赖该接口后，有可以使用该接口，就可以像调用本地服务一样通过dubbo
 *      无感知的调用到provider中的服务，这样方便以后，我们把provider部署到任意能联通
 *      的机器上，实现分布式集群。还可以部署多个provider,实现负载均衡
 */
public interface DubboApi {

    String test(String name);
}
