package com.thisaster.testtask.user.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.thisaster.testtask.user.entity.*;
import com.thisaster.testtask.user.repository.*;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Profile("!test")
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {

        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setName("ADMIN");
        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setName("SUPERVISOR");
        RoleEntity roleEntity3 = new RoleEntity();
        roleEntity3.setName("USER");

        roleRepository.save(roleEntity1);
        roleRepository.save(roleEntity2);
        roleRepository.save(roleEntity3);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("$2a$12$SLqbSCYLtKctGX4ovLuzNeGTo6zV4g0oq1YTX.IoPfHe5UDdM1KDC"); //1234
        user.setEmail("admin@test.ru");
        user.setRoleId(2);
        userRepository.save(user);

    }


}
