package com.jdkhome.blzo.manage.generator.dao;

import com.jdkhome.blzo.manage.generator.model.GroupAuth;
import com.jdkhome.blzo.manage.generator.model.GroupAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupAuthMapper {
    long countByExample(GroupAuthExample example);

    int deleteByExample(GroupAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GroupAuth record);

    int insertSelective(GroupAuth record);

    List<GroupAuth> selectByExample(GroupAuthExample example);

    GroupAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GroupAuth record, @Param("example") GroupAuthExample example);

    int updateByExample(@Param("record") GroupAuth record, @Param("example") GroupAuthExample example);

    int updateByPrimaryKeySelective(GroupAuth record);

    int updateByPrimaryKey(GroupAuth record);
}