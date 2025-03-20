package com.iManager.im.db.api.requestDTO;

import com.iManager.im.db.api.enums.Subscription;
import lombok.Data;
import lombok.NonNull;
import org.apache.kafka.common.protocol.types.Field;

import java.util.UUID;

@Data
public class OrgRequestDTO {
    @NonNull
    UUID id;
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    Subscription subscription;
    String amount;

    public OrgRequestDTO() {
    }

    public OrgRequestDTO(@NonNull UUID id, @NonNull String name, @NonNull String email, @NonNull String password, @NonNull Subscription subscription, String amount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.subscription = subscription;
        this.amount = amount;
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @NonNull String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public @NonNull String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public @NonNull Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(@NonNull Subscription subscription) {
        this.subscription = subscription;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public @NonNull UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }
}
