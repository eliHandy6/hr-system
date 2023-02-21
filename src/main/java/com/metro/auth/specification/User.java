package com.metro.auth.specification;

import com.metro.auth.dtos.UserData;
import jakarta.persistence.*;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  @Column(name = "account_locked")
  private boolean accountNonLocked;

  @Column(name = "account_expired")
  private boolean accountNonExpired;

  @Column(name = "credentials_expired")
  private boolean credentialsNonExpired;
  private String username;
  private String email;
  private String password;
  private  String phoneNumber;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "auth_role_user",
          joinColumns = {
                  @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_auth_user_user_id"))},
          inverseJoinColumns = {
                  @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_auth_user_roles_id"))})
  private Set<Role> roles;
  private boolean enabled;
  private boolean verified;
  private String langKey="en";

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public User(String email, String username, String password, String name) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.name = name;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    roles.forEach(r -> {
      authorities.add(new SimpleGrantedAuthority(r.getName()));
      r.getPermissions()
              .forEach(p -> {
                authorities.add(new SimpleGrantedAuthority(p.getName()));
              });
    });
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public String getPhoneNumber(){
    return phoneNumber;
  }

  public UserData toData() {
    UserData data = new UserData();
    data.setId(this.getId());
    data.setAccountNonExpired(this.accountNonExpired);
    data.setAccountNonLocked(this.accountNonLocked);
    data.setCredentialsNonExpired(this.credentialsNonExpired);
    data.setEmail(this.email);
    data.setEnabled(this.enabled);
    List<String> rolelist = new ArrayList<>();
    this.roles
            .forEach(x -> rolelist.add(x.getName()));
    data.setRoles(rolelist);
    data.setUsername(this.username);
    data.setVerified(this.verified);
    data.setLangKey(this.langKey);
    return  data;
  }
}
