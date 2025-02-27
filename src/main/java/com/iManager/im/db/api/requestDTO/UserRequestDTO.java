package com.iManager.im.db.api.requestDTO;

import com.iManager.im.db.api.enums.Role;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserRequestDTO {
    @NonNull
    String name;
    @NonNull
    String Email;
    @NonNull
    String password;
    @NonNull
    Role role;
    @NonNull
    String OrgId;

}
