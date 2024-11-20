package kz.nikitka.diploma.project.dip.service;

import kz.nikitka.diploma.project.dip.model.Ingredient;
import kz.nikitka.diploma.project.dip.model.Permission;
import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.model.User;
import kz.nikitka.diploma.project.dip.repository.PermissionRepository;
import kz.nikitka.diploma.project.dip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private  final PermissionRepository permissionRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user!=null) return user;
        throw new UsernameNotFoundException("User Not Found");
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public Permission getPermission(Long permissionId){
        return permissionRepository.findById(permissionId).orElseThrow();

    }

    public List<Permission> getPermissions(){
        return permissionRepository.findAll();
    }

    public void assignRole(Long userId,Long roleId){
        User user = getUser(userId);
        Permission permission = getPermission(roleId);
        user.getRoles().add(permission);
        userRepository.save(user);

    }

    public void unassignRole(Long userId,Long roleId){
        User user = getUser(userId);
        Permission permission = getPermission(roleId);
        user.getRoles().remove(permission);
        userRepository.save(user);

    }


    public User updateUser(User user){
        return userRepository.save(user);
    }




}
