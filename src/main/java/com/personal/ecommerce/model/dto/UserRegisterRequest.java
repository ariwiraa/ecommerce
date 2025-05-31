package com.personal.ecommerce.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    @NotBlank(message = "Username tidak boleh kosong")
    @Size(min = 3, max = 20, message = "Username harus antara 3 dan 20 karakter")
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 8, max = 100, message = "Password harus antara 8 dan 100 karakter")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,100}$",
            message = "Password harus memiliki kombinasi huruf besar, huruf kecil, dan angka"
    )
    private String password;
}
