package com.zahangir.konasl.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity(name = "role")
public class Role extends BaseModel{
    @Column(name = "name",unique = true, nullable = false)
    private String name;
    @Column(name = "role",unique = true, nullable = false)
    private String role;
}
