package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    public static final String GENDER_MALE ="M";
    public static final String GENDER_FEMALE = "F";

    private String userName;
    private String gender;
    private String password;

    @Override
    public String toString() {
        return "UserAccount{" +
                "userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
