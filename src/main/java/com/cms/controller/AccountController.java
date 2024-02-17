package com.cms.controller;

import com.cms.entity.Account;
import com.cms.entity.Role;
import com.cms.dao.AccountRepository;
import com.cms.dao.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Account account) {
        Role role = getOrCreateRole(Role.RoleEnum.ADMIN);
        account.setRole(role);
        Account savedAccount = accountRepository.save(account);
        if (savedAccount != null) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed");
        }
        }

        private Role getOrCreateRole(Role.RoleEnum roleEnum) {
            Optional<Role> existingRole = roleRepository.findByRole(roleEnum);
            return existingRole.orElseGet(() -> {
                Role newRole = new Role();
                newRole.setRole(roleEnum);
                return roleRepository.save(newRole);
            });
        }
    }

