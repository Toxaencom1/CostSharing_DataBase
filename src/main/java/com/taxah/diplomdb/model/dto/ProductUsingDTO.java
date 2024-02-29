package com.taxah.diplomdb.model.dto;

import com.taxah.diplomdb.model.TempUser;
import lombok.Data;

import java.util.List;

@Data
public class ProductUsingDTO {
    private Long checkId;
    private String productName;
    private Double cost;
    private List<TempUser> tempUsers;
}
