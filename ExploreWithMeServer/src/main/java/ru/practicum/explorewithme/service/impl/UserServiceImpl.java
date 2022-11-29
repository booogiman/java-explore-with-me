package ru.practicum.explorewithme.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.user.UserDto;
import ru.practicum.explorewithme.dto.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.User;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsersById(int[] ids) {
        return UserMapper.userToDtoList(userRepository.findAllById(
                Arrays.stream(ids).boxed().collect(Collectors.toList())));
    }

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.dtoToUser(userDto));
        return UserMapper.userToDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntryNotFoundException("Отсутствует пользователь с id: " + userId));
    }
}
