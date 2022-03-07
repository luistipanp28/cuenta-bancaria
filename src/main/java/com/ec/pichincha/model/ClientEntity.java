package com.ec.pichincha.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class ClientEntity extends PersonEntity {

	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private int idclient;
	   
	   private String password;
	
	   private boolean status;
    
}
