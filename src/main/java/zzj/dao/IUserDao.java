package zzj.dao;

import org.apache.ibatis.annotations.Param;

import zzj.bean.User;

public interface IUserDao {
    User findById(int id);
    
    User findByUserName(@Param(value="username") String username);
}
