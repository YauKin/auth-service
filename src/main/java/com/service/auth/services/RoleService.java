package com.service.auth.services;

import com.service.auth.constants.ErrorType;
import com.service.auth.constants.RoleEnum;
import com.service.auth.dao.Role;
import com.service.auth.exceptions.FunctionalException;
import com.service.auth.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(RoleEnum roleName) throws FunctionalException {
        return roleRepository.findByName(roleName).orElseThrow(() -> new FunctionalException(ErrorType.ROLE_NOT_FOUND));
    }

}
