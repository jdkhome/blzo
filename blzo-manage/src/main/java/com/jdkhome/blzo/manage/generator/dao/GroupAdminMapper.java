package com.jdkhome.blzo.manage.generator.dao;

import com.jdkhome.blzo.manage.generator.model.GroupAdmin;
import com.jdkhome.blzo.manage.generator.model.GroupAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupAdminMapper {
    long countByExample(GroupAdminExample example);

    int deleteByExample(GroupAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GroupAdmin record);

    int insertSelective(GroupAdmin record);

    List<GroupAdmin> selectByExample(GroupAdminExample example);

    GroupAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GroupAdmin record, @Param("example") GroupAdminExample example);

    int updateByExample(@Param("record") GroupAdmin record, @Param("example") GroupAdminExample example);

    int updateByPrimaryKeySelective(GroupAdmin record);

    int updateByPrimaryKey(GroupAdmin record);
}