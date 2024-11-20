package kz.nikitka.diploma.project.dip.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import kz.nikitka.diploma.project.dip.model.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {

    private String userEmail;
    private String userPassword;
    private String userFullName;
    private String userAva;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> userRoles;
}
