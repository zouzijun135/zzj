package zzj.service;

import zzj.bean.User;

public interface IUserService {
    User getUserById(int userId);
    
    User getUserByName(String userName);

}
