package com.gnc.websocketspringmongo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(User user){
        var connectedUser = userRepository.findById(user.getNickName()).orElse(null);
        if(connectedUser != null){
            connectedUser.setStatus(Status.OFFLINE);
            userRepository.save(connectedUser);
        }


    }
    public List<User> findConnectedUsers(){
        return userRepository.findAllByStatus(Status.ONLINE);
    }
}
