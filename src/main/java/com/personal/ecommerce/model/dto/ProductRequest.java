package com.personal.ecommerce.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Nama produk tidak boleh kosong")
    @Size(min = 3, max = 20, message = "Nama produk harus antara 3 dan 20 karakter")
    private String name;

    @NotNull(message = "Harga tidak boleh kosong")
    @Positive(message = "Harga harus lebih besar dari 0")
    @Digits(integer = 10, fraction = 2, message = "Harga harus memiliki maksimal 10 digit dan 2 angka dibelakang koma")
    private BigDecimal price;

    @NotBlank(message = "Deskripsi produk tidak boleh kosong")
    @Size(max = 1000, message = "Deskripsi Produk tidak boleh lebih dari 1000 karakter")
    private String description;

    @PositiveOrZero(message = "Stock tidak boleh kurang dari 0")
    private Integer stockQuantity;

    private BigDecimal weight;

    private List<Long> categories;
}
