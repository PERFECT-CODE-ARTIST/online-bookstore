package com.bookstore.online.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class UserEntity implements Serializable {

  @Id
  private String userId;
  private String name;
  private String email;
  private String password;
}
