package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.common.RoleType;
import dev.kevinyu.service.restful.model.UserDO;
import dev.kevinyu.service.restful.model.UserVO;
import dev.kevinyu.service.restful.repository.UserRepository;
import dev.kevinyu.service.restful.service.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserVO> getList() {
        List<UserDO> userDOs = userRepository.findAll();
        List<UserVO> userVOs = userDOs.stream().map(userDO -> convertToUserVO(userDO)).collect(Collectors.toList());

        return userVOs;
    }

    @Override
    public UserVO getById(String id) {
        UserDO userDO = userRepository.findById(new ObjectId(id)).get();
        UserVO userVO = convertToUserVO(userDO);

        return userVO;
    }

    @Override
    public UserVO getByName(String username) {
        UserDO userDO = userRepository.findUserDOByUsername(username).get();
        UserVO userVO = convertToUserVO(userDO);

        return userVO;
    }

    @Override
    public UserVO post(UserVO user) {
        UserDO userDO = convertToUserDO(user);
        userDO = userRepository.insert(userDO);
        UserVO userVO = convertToUserVO(userDO);

        return userVO;
    }

    @Override
    public UserVO update(String id, UserVO user) {
        boolean existed = userRepository.existsById(new ObjectId(id));
        if(!existed){
            return null;
        }

        UserDO userDO = convertToUserDO(user);
        userDO = userRepository.save(userDO);
        UserVO userVO = convertToUserVO(userDO);

        return userVO;
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(new ObjectId(id));
    }

    private UserVO convertToUserVO(UserDO userDO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        userVO.setUserId(userDO.getUserId().toString());
        userVO.setRole(RoleType.valueOf(userDO.getRole()));

        return userVO;
    }

    private UserDO convertToUserDO(UserVO userVO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userVO, userDO);
        userDO.setUserId(new ObjectId(userVO.getUserId()));
        userDO.setRole(userVO.getRole().getCode());

        return userDO;
    }
}
