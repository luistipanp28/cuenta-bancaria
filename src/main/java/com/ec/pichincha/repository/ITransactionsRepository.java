package com.ec.pichincha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ec.pichincha.model.TransactionsEntity;

public interface ITransactionsRepository extends JpaRepository<TransactionsEntity, Long>{

}
