package com.lsqingfeng.action.knowledge.copy;

import com.baidu.unbiz.easymapper.MapperFactory;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @className: BeanCopyDemo
 * @description: 实体拷贝方法总结：
 * @author: sh.Liu
 * @date: 2020-06-01 09:54
 */
public class BeanCopyDemo {

    @Test
    public void test() throws  Exception{
        // 先设置一个有值的对象
        Person p = new Person();
        p.setId(1);
        p.setName("张三");
        p.setPrice(new BigDecimal("100"));
        p.setVipFlag(true);
        p.setCreateTime(new Date());
        p.setUpdateTime(LocalDateTime.now());
        System.out.println(p);

        // 创建一个vo, 将实体拷贝给vo
        PersonVO p2 = new PersonVO();

        // 方式1： BeanUtils， 需引入commons-beanutils
        long start = System.currentTimeMillis();
        BeanUtils.copyProperties(p2, p);

        // apache BeanUtils 耗时:104毫秒
        System.out.println("apache BeanUtils 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("拷贝之后结果：" + p2);

        System.out.println("----------------------------");

        // 方式2： 使用springBeanUtils
        start = System.currentTimeMillis();
        p2 = new PersonVO();
        org.springframework.beans.BeanUtils.copyProperties(p, p2);
        // spring BeanUtils 耗时:56毫秒
        System.out.println("spring BeanUtils 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("拷贝之后结果：" + p2);

        System.out.println("----------------------------");

        // 方式3： 使用BeanCopier 是cglib提供的
        p2 = new PersonVO();
        start = System.currentTimeMillis();
        // cglib BeanCopier 不算初始化耗时:0毫秒 算上初始化40毫秒
        BeanCopier beanCopier = BeanCopier.create(Person.class, PersonVO.class, false);
        // p 是源  p2 是目标
        beanCopier.copy(p, p2, null);
        System.out.println("cglib BeanCopier 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("拷贝之后结果：" + p2);

        System.out.println("----------------------------");

        //方式4： dozer: 一个实体拷贝框架，支持深拷贝，可自定义映射（名称不同的字段间进行拷贝）
        // 需引入jar包，需兼容jdk8
        // 并且不支持localDateTime
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
        start = System.currentTimeMillis();
        PersonVO p3 = mapper.map(p, PersonVO.class);
        // dozer 耗时:119毫秒
        System.out.println("dozer 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("拷贝之后结果：" + p3);

        // 方式5： ModelMap
        // 需引入jar: org.modelmapper
        // 支持自定义映射，支持List map
        start = System.currentTimeMillis();
        ModelMapper modelMapper = new ModelMapper();

        PersonVO p4 = modelMapper.map(p, PersonVO.class);
        // modelMapper 耗时:37毫秒
        System.out.println("modelMapper 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("拷贝之后结果：" + p4);


        // 方式6： EasyMapper:
        // http://neoremind.com/2016/08/easy-mapper-%E4%B8%80%E4%B8%AA%E7%81%B5%E6%B4%BB%E5%8F%AF%E6%89%A9%E5%B1%95%E7%9A%84%E9%AB%98%E6%80%A7%E8%83%BDbean-mapping%E7%B1%BB%E5%BA%93/
        start = System.currentTimeMillis();
        PersonVO p5 = MapperFactory.getCopyByRefMapper()
                .mapClass(Person.class, PersonVO.class)
                .registerAndMap(p, PersonVO.class);
        // easyMapper 耗时:88毫秒
        System.out.println("easyMapper 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("拷贝之后结果：" + p5);

        // 6. orika:125ms
        start = System.currentTimeMillis();
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        MapperFacade orikaMapper = mapperFactory.getMapperFacade();
        PersonVO p6 = orikaMapper.map(p, PersonVO.class);
        System.out.println("orika 耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        System.out.println("拷贝之后结果：" + p6);

    }


    /**
     * apache BeanUtils:    耗时:100毫秒
     * apache PropertyUtils:
     * spring BeanUtils:    耗时:48毫秒
     * cglib  BeanCopier:   耗时:42毫秒： 包括初始化， 不包括0
     * dozer:               耗时:101毫秒
     * ModelMapper:         耗时:28毫秒
     * easyMapper           耗时:88毫秒
     * orika                耗时:125  不算初始化58
     */
    @Test
    public void listTest(){
        List<Person> list = new ArrayList<>();
        for (int i=0; i<5;i++ ) {
            Person p = new Person();
            p.setId(i);
            p.setName("张胜男" + i);
            p.setPrice(new BigDecimal(12));
            p.setCreateTime(new Date());
            p.setUpdateTime(LocalDateTime.now()
            );
            p.setVipFlag(i%2 == 0);
            list.add(p);
        }


        ModelMapper modelMapper = new ModelMapper();
        List<PersonVO> list2 =  modelMapper.map(list, new TypeToken<List<PersonVO>>() {}.getType());
        System.out.println(list2);
    }

}




