package com.zahangir.konasl.model;



import com.zahangir.konasl.utils.SecurityUtils;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable {
    public static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    private Long createdBy;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", updatable = false)
    private Long updatedBy;

    @PrePersist
    public void initProperties(){
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        if(SecurityUtils.getCurrentUser() != null){
            this.createdBy = SecurityUtils.getCurrentUser().getId();
        }
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
        if(SecurityUtils.getCurrentUser() != null){
            this.updatedBy = SecurityUtils.getCurrentUser().getId();
        }
    }
}
