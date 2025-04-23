package com.iManager.im.db.api.utils;

import com.iManager.im.db.api.model.Operation;
import com.iManager.im.db.api.model.Organization;
import com.iManager.im.db.api.model.Roles;
import com.iManager.im.db.api.model.User;
import com.iManager.im.db.api.repository.OperationRepository;
import com.iManager.im.db.api.repository.OrgRepository;
import com.iManager.im.db.api.repository.RoleRepository;
import com.iManager.im.db.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValidateAuth {
    @Autowired
    OrgRepository orgRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public boolean validateUser(String loggedId,Operation operation){
        Optional<Organization> opOrganization = orgRepository.findByEmail(loggedId);
        Optional<User> user = userRepository.findByEmailWithSubProjectRole(loggedId);
        if(opOrganization.isPresent()){
            return true;
        }
        else{
            if(user.isPresent()){
                Map<UUID, Roles> rolesMap = user.get().getSubProjectRole();
                for(Roles role : rolesMap.values()){
                    Roles roles = roleRepository.findByIdWithOperations(role.getId())
                            .orElseThrow(()-> new RuntimeException("You are not authorized to for this operation"));
                    List<Operation> operations = roles.getOperations();
                    for(Operation op : operations){
                        if(op.getId().equals(operation.getId()))
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
