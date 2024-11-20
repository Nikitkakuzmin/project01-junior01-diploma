package kz.nikitka.diploma.project.dip.repository;

import jakarta.transaction.Transactional;
import kz.nikitka.diploma.project.dip.model.Permission;
import kz.nikitka.diploma.project.dip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Permission findByPermission(String permission);
}
