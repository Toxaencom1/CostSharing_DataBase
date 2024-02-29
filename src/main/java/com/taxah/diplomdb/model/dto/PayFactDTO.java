package com.taxah.diplomdb.model.dto;

import com.taxah.diplomdb.model.TempUser;
import lombok.Data;

@Data
public class PayFactDTO {
    Long tempUserId;
    Double amount;
    Long sessionId;
}
