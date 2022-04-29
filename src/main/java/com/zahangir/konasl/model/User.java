package com.zahangir.konasl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "user")
public class User extends BaseModel {
    private String username;
    private String password;
//    private String name;
//    @Column(name = "mobile_no")
//    @NotNull(message = "Please provide your mobile no")
//    @NotEmpty(message = "Mobile No can't be empty")
//    @Pattern(regexp = "^[0][1]\\d{9}", message = "Please Provide a valid mobile number")
//    private String mobileNo;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}