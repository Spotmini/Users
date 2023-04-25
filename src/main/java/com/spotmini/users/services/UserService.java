package com.spotmini.users.services;

import com.spotmini.users.entities.UserEntity;
import com.spotmini.users.models.UserModel;
import com.spotmini.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserEntity userEntity = null;

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userEntity.getUsername());
        userEntity.setPassword(userEntity.getPassword());

        return userRepository.save(userEntity);
    }

    public UserEntity loginUser(String username, String password) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            if (userEntity.getPassword().equals(password)) {
                this.userEntity = userEntity;
                return userEntity;
            }
        }

        return null;
    }

    public void updateUserRole(Long userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserEntity userToUpdate = user.get();
            userToUpdate.setSpecial(true);
            userRepository.save(userToUpdate);
        }
    }

    public boolean isUserSpecial() {
        return userEntity.getSpecial();
    }

    public Long currentUserID(){
        return userEntity.getId();
    }

}

