package com.increff.store.pojo;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit {
//    updation time stamp lagane se creation time stamp nhi kaam kar rha hai
//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
