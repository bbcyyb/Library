package dev.kevinyu.service.restful.service;

import dev.kevinyu.service.restful.model.UserVO;

import java.util.List;

public interface UsersService {

    List<UserVO> getList();

    UserVO getById(String id);

    UserVO getByName(String username);

    UserVO post(UserVO user);

    UserVO update(String id, UserVO user);

    void delete(String id);
}
