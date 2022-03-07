package com.ec.pichincha.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public class PersonEntity  {
	
    @Id
    private String idPerson;

    private String name;

    private String gender;

    private int age;

    private String address;

    private String phoneNumber;
}
