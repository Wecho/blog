package com.wecho.core.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BlogMapper<T> extends Mapper<T>, MySqlMapper<T>,InsertListMapper<T> {
}