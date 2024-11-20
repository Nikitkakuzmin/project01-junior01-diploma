package kz.nikitka.diploma.project.dip.service;

import kz.nikitka.diploma.project.dip.model.Permission;
import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> getPermissions(){
        return permissionRepository.findAll();
    }


    public Permission getPermission(Long id){
        return permissionRepository.findById(id).orElseThrow();
    }
}
