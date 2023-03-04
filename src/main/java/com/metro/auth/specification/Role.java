package com.metro.auth.specification;

import com.metro.auth.dtos.RoleData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "auth_role", schema="hrm")
@Builder
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auth_permission_role",
            joinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_role__id"))},
            inverseJoinColumns = {
                    @JoinColumn(name = "permission_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_permission_id"))}, uniqueConstraints = {
            @UniqueConstraint(name = "ROLE_PERMISSION_UK_auth_permission_role", columnNames = {"role_id", "permission_id"})})
    private Set<Permission> permissions = new HashSet<>();
    private String description;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {

    }

    public boolean addPermission(Permission permission){
        return this.permissions.add(permission);
    }
    public boolean removePermissions(Permission permission){
        return this.permissions.remove(permission);
    }
    public Collection<Permission> getPermission(){
        return this.permissions;
    }
    public boolean hasPermission(final String permission){
        boolean isPermitted = false;
        for(Permission permissionModel : permissions){
            if (permissionModel.isMatch(permission)){
                isPermitted = true;
                break;
            }
        }
        return isPermitted;
    }
    public boolean updatePermission(Permission permission, final boolean toUpdate){

        if(toUpdate){
            return this.permissions.add(permission);
        }else{
            return this.permissions.remove(permission);
        }
    }
    public boolean updateAllPermissions(List<Permission> permissions){
        this.permissions.clear();
        return this.permissions.addAll(permissions);
    }
    public RoleData toData(){
        return new RoleData(this.id, this.name, this.description, this.isDisabled);
    }
}
