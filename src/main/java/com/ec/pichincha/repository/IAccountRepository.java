package com.ec.pichincha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ec.pichincha.model.AccountEntity;

public interface IAccountRepository extends JpaRepository<AccountEntity, Long> {

}
