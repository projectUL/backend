
package com.example.demo.ExtraClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEdit {
    
    private String oldEmail;
    private String newEmail;
    private String oldPassword;
    private String newPassword;
    private String confirmNew;
}
