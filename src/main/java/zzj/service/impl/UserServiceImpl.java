package zzj.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zzj.bean.User;
import zzj.dao.IUserDao;
import zzj.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;
    @Override
    public User getUserById(int userId) {
        // TODO Auto-generated method stub
        return this.userDao.findById(userId);
    }
    @Override
    public User getUserByName(String userName) {
        // TODO Auto-generated method stub
        return this.userDao.findByUserName(userName);
    }
}
