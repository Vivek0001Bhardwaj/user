package com.techolution.user.payload;

import com.techolution.user.model.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime joinedAt;
    private String email;
    private Address address;
    private String phone;
}
