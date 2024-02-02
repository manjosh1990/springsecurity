package com.manjosh.practice.client.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
    private final static  int EXPIRATION_TIME =10;

    public VerificationToken(CustomUser user, String token){
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationData();
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationData();
    }

        private Date calculateExpirationData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, VerificationToken.EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Date expirationTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private CustomUser user;
}
