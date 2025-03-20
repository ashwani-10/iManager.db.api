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

    public UserRequestDTO() {
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @NonNull String getEmail() {
        return Email;
    }

    public void setEmail(@NonNull String email) {
        Email = email;
    }

    public @NonNull String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public UserRequestDTO(@NonNull String name, @NonNull String email, @NonNull String password) {
        this.name = name;
        Email = email;
        this.password = password;
    }
}
