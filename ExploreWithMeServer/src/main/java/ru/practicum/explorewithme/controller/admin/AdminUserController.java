package ru.practicum.explorewithme.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.user.UserDto;
import ru.practicum.explorewithme.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin")
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> getUsersById(@RequestParam int[] ids) {
        log.info("Администратор запросил пользователей = {}", ids);
        return userService.getUsersById(ids);
    }

    @PostMapping("/users")
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Администратор добавил пользователя = {}", userDto);
        return userService.addUser(userDto);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("Администратор удалил пользователя = {}", id);
        userService.deleteUser(id);
    }
}
