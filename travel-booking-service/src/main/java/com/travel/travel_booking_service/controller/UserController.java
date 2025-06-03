package com.travel.travel_booking_service.controller;

import org.springframework.web.bind.annotation.*;

import com.travel.travel_booking_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    private final UserService userService;

    //    @PostMapping("/register")
    //    public ResponseEntity<ApiResponse<UserDTO>> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
    //        return new ResponseEntity<>(userService.registerUser(registrationDTO), HttpStatus.CREATED);
    //    }

    //    @PutMapping("/{id}")
    //    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO updateDTO) {
    //        return ResponseEntity.ok(userService.updateUser(id, updateDTO));
    //    }
    //
    //    @DeleteMapping("/{id}")
    //    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    //        userService.deleteUser(id);
    //        return ResponseEntity.noContent().build();
    //    }
    //
    //    @GetMapping("/{id}")
    //    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    //        return ResponseEntity.ok(userService.getUserById(id));
    //    }
    //
    //    @GetMapping("/email/{email}")
    //    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
    //        return ResponseEntity.ok(userService.getUserByEmail(email));
    //    }
    //
    //    @GetMapping("/username/{username}")
    //    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
    //        return ResponseEntity.ok(userService.getUserByUsername(username));
    //    }
    //
    //    @GetMapping
    //    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
    //        return ResponseEntity.ok(userService.getAllUsers(pageable));
    //    }
    //
    //    @PostMapping("/{id}/change-password")
    //    public ResponseEntity<Void> changePassword(
    //            @PathVariable Long id,
    //            @RequestParam String oldPassword,
    //            @RequestParam String newPassword) {
    //        userService.changePassword(id, oldPassword, newPassword);
    //        return ResponseEntity.ok().build();
    //    }
    //
    //    @PostMapping("/reset-password")
    //    public ResponseEntity<Void> resetPassword(@RequestParam String email) {
    //        userService.resetPassword(email);
    //        return ResponseEntity.ok().build();
    //    }
    //
    //    @PostMapping("/{id}/activate")
    //    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
    //        userService.activateUser(id);
    //        return ResponseEntity.ok().build();
    //    }
    //
    //    @PostMapping("/{id}/deactivate")
    //    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
    //        userService.deactivateUser(id);
    //        return ResponseEntity.ok().build();
    //    }
    //
    //    @PutMapping("/{id}/role")
    //    public ResponseEntity<Void> updateUserRole(@PathVariable Long id, @RequestParam String role) {
    //        userService.updateUserRole(id, role);
    //        return ResponseEntity.ok().build();
    //    }
    //
    //    @GetMapping("/check-email")
    //    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
    //        return ResponseEntity.ok(userService.isEmailExists(email));
    //    }
    //
    //    @GetMapping("/check-username")
    //    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
    //        return ResponseEntity.ok(userService.isUsernameExists(username));
    //    }
}
