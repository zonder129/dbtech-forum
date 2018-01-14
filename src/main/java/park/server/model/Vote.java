package park.server.model;

import java.util.Objects;

/**
 * Информация о голосовании пользователя. 
 */

public class Vote   {
  private String nickname;

  private Integer voice;

  public Vote() {

  }

   /**
   * Идентификатор пользователя.
   * @return nickname
  **/

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

   /**
   * Отданный голос.
   * @return voice
  **/

  public Integer getVoice() {
    return voice;
  }

  public void setVoice(Integer voice) {
    this.voice = voice;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vote vote = (Vote) o;
    return Objects.equals(this.nickname, vote.nickname) &&
        Objects.equals(this.voice, vote.voice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nickname, voice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vote {\n");
    
    sb.append("    nickname: ").append(toIndentedString(nickname)).append("\n");
    sb.append("    voice: ").append(toIndentedString(voice)).append("\n");
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

