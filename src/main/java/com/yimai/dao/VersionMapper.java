package com.yimai.dao;

import com.yimai.entity.Version;

public interface VersionMapper {
    int deleteByPrimaryKey(Integer version);

    int insert(Version record);

    int insertSelective(Version record);
}