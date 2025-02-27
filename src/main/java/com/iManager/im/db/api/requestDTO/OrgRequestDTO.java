package com.iManager.im.db.api.requestDTO;

import com.iManager.im.db.api.enums.Subscription;
import lombok.Data;
import lombok.NonNull;

@Data
public class OrgRequestDTO {
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    Subscription subscription;

}
