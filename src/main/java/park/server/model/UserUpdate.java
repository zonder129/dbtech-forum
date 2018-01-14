package park.server.model;

import java.util.Objects;

/**
 * Информация о пользователе. 
 */

public class UserUpdate {
  private String about;

  private String email;

  private String fullname;

  public UserUpdate() {

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserUpdate userUpdate = (UserUpdate) o;
    return Objects.equals(this.about, userUpdate.about) &&
        Objects.equals(this.email, userUpdate.email) &&
        Objects.equals(this.fullname, userUpdate.fullname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(about, email, fullname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserUpdate {\n");
    
    sb.append("    about: ").append(toIndentedString(about)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    fullname: ").append(toIndentedString(fullname)).append("\n");
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

