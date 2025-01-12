package org.example.authentication.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

        private String email;
        private String password;
        private String name;

        public RegisterRequest() {
        }
    }

