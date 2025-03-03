package com.maverickdevs.expensebuddy.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    private String username;

    private String password;
//    private Long phoneNumber;

    private String email;
}
