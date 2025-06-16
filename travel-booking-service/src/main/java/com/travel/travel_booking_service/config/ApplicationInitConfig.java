package com.travel.travel_booking_service.config;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.travel.travel_booking_service.entity.Permission;
import com.travel.travel_booking_service.entity.Role;
import com.travel.travel_booking_service.entity.RolePermission;
import com.travel.travel_booking_service.entity.User;
import com.travel.travel_booking_service.enums.PermissionEnum;
import com.travel.travel_booking_service.enums.RoleEnum;
import com.travel.travel_booking_service.repository.PermissionRepository;
import com.travel.travel_booking_service.repository.RolePermissionRepository;
import com.travel.travel_booking_service.repository.RoleRepository;
import com.travel.travel_booking_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RolePermissionRepository rolePermissionRepository;

    // Khởi tạo user Admin
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            // 1. Lấy map mã - mô tả permission từ enum
            Map<String, String> permissionMap = PermissionEnum.getType();

            // 2. Lưu permission nếu chưa tồn tại
            List<Permission> existingPermissions = permissionRepository.findAll();
            Set<String> existingCodes =
                    existingPermissions.stream().map(Permission::getCode).collect(Collectors.toSet());

            List<Permission> newPermissions = permissionMap.entrySet().stream()
                    .filter(entry -> !existingCodes.contains(entry.getKey()))
                    .map(entry -> Permission.builder()
                            .code(entry.getKey())
                            .description(entry.getValue())
                            .build())
                    .collect(Collectors.toList());

            if (!newPermissions.isEmpty()) {
                permissionRepository.saveAll(newPermissions);
                log.info("Saved {} new permissions.", newPermissions.size());
            }

            // 3. Tạo hoặc lấy role ADMIN
            Role adminRole = roleRepository.findByCode(RoleEnum.ADMIN.name()).orElseGet(() -> {
                Role role = Role.builder()
                        .code(RoleEnum.ADMIN.name())
                        .description(RoleEnum.ADMIN.getDescription())
                        .build();
                Role savedRole = roleRepository.save(role);
                log.info("Created ADMIN role.");
                return savedRole;
            });

            // 4. Kiểm tra các permission đã được liên kết với role ADMIN chưa
            List<RolePermission> existingRolePermissions = rolePermissionRepository.findByRole(adminRole);
            Set<Long> linkedPermissionIds = existingRolePermissions.stream()
                    .map(rp -> rp.getPermission().getId())
                    .collect(Collectors.toSet());

            // Lấy tất cả permission hiện có (bao gồm cả mới tạo)
            List<Permission> allPermissions = permissionRepository.findAll();

            // 5. Tạo các RolePermission cho permission chưa liên kết
            List<RolePermission> newRolePermissions = allPermissions.stream()
                    .filter(p -> !linkedPermissionIds.contains(p.getId()))
                    .map(p -> {
                        RolePermission rp = new RolePermission();
                        rp.setRole(adminRole);
                        rp.setPermission(p);
                        return rp;
                    })
                    .collect(Collectors.toList());

            if (!newRolePermissions.isEmpty()) {
                rolePermissionRepository.saveAll(newRolePermissions);
                log.info("Added {} new RolePermission links for ADMIN role.", newRolePermissions.size());
            }

            // 6. Tạo user admin nếu chưa có
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123456"))
                        .email("duyduongtourism@gmail.com")
                        .phone("0824783053")
                        .fullname("Quản trị viên")
                        .role(adminRole)
                        .build();

                userRepository.save(user);
                log.warn(
                        "Admin user has been created with default password: admin123456. Please change it immediately!");
            }
        };
    }
}
