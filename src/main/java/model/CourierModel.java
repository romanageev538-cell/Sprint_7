package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor          // генерирует конструктор со всеми полями
@NoArgsConstructor           // генерирует пустой конструктор
public class CourierModel {
    private String login;
    private String password;
    private String firstName;

}
