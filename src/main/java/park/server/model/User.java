package park.server.model;

import java.util.Objects;

import javax.validation.constraints.*;

/**
 * Информация о пользователе. 
 */

public class User {
  private String about;

  private String email;

  private String fullname;

  private String nickname;

  public User() {

  }

  public User(String about, String email, String fullname, String nickname) {
    this.about = about;
    this.email = email;
    this.fullname = fullname;
    this.nickname = nickname;
  }

   /**
   * Описание пользователя.
   * @return about
  **/

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

   /**
   * Почтовый адрес пользователя (уникальное поле).
   * @return email
  **/

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

   /**
   * Полное имя пользователя.
   * @return fullname
  **/

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

   /**
   * Имя пользователя (уникальное поле). Данное поле допускает только латиницу, цифры и знак подчеркивания. Сравнение имени регистронезависимо. 
   * @return nickname
  **/

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.about, user.about) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.fullname, user.fullname) &&
        Objects.equals(this.nickname, user.nickname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(about, email, fullname, nickname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    about: ").append(toIndentedString(about)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    fullname: ").append(toIndentedString(fullname)).append("\n");
    sb.append("    nickname: ").append(toIndentedString(nickname)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

