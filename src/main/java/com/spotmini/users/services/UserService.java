package com.spotmini.users.services;

import com.spotmini.users.entities.UserEntity;
import com.spotmini.users.models.UserModel;
import com.spotmini.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserModel createUser(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userModel.getUsername());
        userEntity.setPassword(userModel.getPassword());
        userEntity.setAdmin(userModel.isSpecial());

        UserEntity savedUserEntity = userRepository.save(userEntity);

        UserModel savedUserModel = new UserModel();
        savedUserModel.setId(savedUserEntity.getId());
        savedUserModel.setUsername(savedUserEntity.getUsername());
        savedUserModel.setAdmin(savedUserEntity.isSpecial());

        return savedUserModel;
    }

    public UserModel loginUser(String username, String password) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            if (userEntity.getPassword().equals(password)) {
                UserModel userModel = new UserModel();
                userModel.setId(userEntity.getId());
                userModel.setUsername(userEntity.getUsername());
                userModel.setAdmin(userEntity.isSpecial());

                return userModel;
            }
        }

        return null;
    }
}

