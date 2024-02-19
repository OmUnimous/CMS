package com.cms.dao;

import com.cms.entity.Account;
import com.cms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account save(Account account);

    Optional<Account> findById(Long id);

    void save(Role role);
}
