package kz.nikitka.diploma.project.dip.model;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity implements GrantedAuthority  {

    private String permission;


    @Override
    public String getAuthority() {
        return permission;
    }
}
