package com.ec.pichincha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ec.pichincha.model.ClientEntity;

public interface IClientRepository extends JpaRepository<ClientEntity, String>{

}
