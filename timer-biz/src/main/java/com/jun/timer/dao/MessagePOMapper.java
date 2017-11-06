package com.jun.timer.dao;


import com.jun.timer.dao.po.MessagePO;

public interface MessagePOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessagePO record);

    int insertSelective(MessagePO record);

    MessagePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessagePO record);

    int updateByPrimaryKey(MessagePO record);
}