package park.server.model;

import java.util.Objects;

/**
 * Сообщение для обновления ветки обсуждения на форуме. Пустые параметры остаются без изменений. 
 */

public class ThreadUpdate   {
  private String message;

  private String title;

  public ThreadUpdate() {

  }

  public ThreadUpdate message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Описание ветки обсуждения.
   * @return message
  **/

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

   /**
   * Заголовок ветки обсуждения.
   * @return title
  **/

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ThreadUpdate threadUpdate = (ThreadUpdate) o;
    return Objects.equals(this.message, threadUpdate.message) &&
        Objects.equals(this.title, threadUpdate.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ThreadUpdate {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

