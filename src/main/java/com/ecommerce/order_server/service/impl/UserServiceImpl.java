package com.ecommerce.order_server.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.order_server.dto.UserDto;
import com.ecommerce.order_server.entity.User;
import com.ecommerce.order_server.exception.ResourceNotFoundException;
import com.ecommerce.order_server.mapper.UserMapper;
import com.ecommerce.order_server.repository.UserRepository;
import com.ecommerce.order_server.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(user);
        return UserMapper.toUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with given id: " + userId));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
    }
}
