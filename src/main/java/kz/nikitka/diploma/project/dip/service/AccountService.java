package kz.nikitka.diploma.project.dip.service;

import kz.nikitka.diploma.project.dip.model.Permission;
import kz.nikitka.diploma.project.dip.model.User;
import kz.nikitka.diploma.project.dip.repository.PermissionRepository;
import kz.nikitka.diploma.project.dip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final UserService userService;
    public User registerUser(String email, String password, String rePassword, String fullName){
        User checkUser = userRepository.findByEmail(email);
        if(checkUser==null){
            if(password.equals(rePassword)){
                List<Permission> permissions = new ArrayList<>();
                Permission userPermission = permissionRepository.findByPermission("ROLE_USER");
                permissions.add(userPermission);
                User user = User
                        .builder()
                        .email(email)
                        .roles(permissions)
                        .fullName(fullName)
                        .password(passwordEncoder.encode(password))
                        .build();
                return userRepository.save(user);
            }


        }
        return null;
    }

    public User updatePassword(String oldPassword, String newPassword, String repeatNewPassword){
        User currentUser = userService.getCurrentUser();
        if(currentUser!=null){
            if(newPassword.equals(repeatNewPassword) &&
                    passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                return userRepository.save(currentUser);
            }
        }
        return null;
    }
}
