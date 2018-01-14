package park.server.model;

import java.util.Objects;
import javax.validation.constraints.*;

/**
 * Информация о форуме. 
 */

public class Forum   {
    private long posts;

    private String slug;

    private int threads;

    private String title;

    private String user;

    public Forum(){
    }

    public Forum(Long posts, String slug, int threads, String title, String user) {
        this.posts = posts;
        this.slug = slug;
        this.threads = threads;
        this.title = title;
        this.user = user;
    }

    /**
    * Общее кол-во сообщений в данном форуме.
    * @return posts
    **/
    public long getPosts() {
    return posts;
    }

    public void setPosts(long posts) {
    this.posts = posts;
    }

    /**
    * Человекопонятный URL (https://ru.wikipedia.org/wiki/), уникальное поле.
    * @return slug
    **/
    @Pattern(regexp="^(\\d|\\w|-|_)*(\\w|-|_)(\\d|\\w|-|_)*$")
    public String getSlug() {
    return slug;
    }

    public void setSlug(String slug) {
    this.slug = slug;
    }

    /**
    * Общее кол-во ветвей обсуждения в данном форуме.
    * @return threads
    **/
    public int getThreads() {
    return threads;
    }

    public void setThreads(int threads) {
    this.threads = threads;
    }

    /**
    * Название форума.
    * @return title
    **/
    public String getTitle() {
    return title;
    }

    public void setTitle(String title) {
    this.title = title;
    }

    /**
    * Nickname пользователя, который отвечает за форум.
    * @return user
    **/
    public String getUser() {
    return user;
    }

    public void setUser(String user) {
    this.user = user;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }
        Forum forum = (Forum) o;
        return Objects.equals(this.posts, forum.posts) &&
            Objects.equals(this.slug, forum.slug) &&
            Objects.equals(this.threads, forum.threads) &&
            Objects.equals(this.title, forum.title) &&
            Objects.equals(this.user, forum.user);
    }

    @Override
    public int hashCode() {
    return Objects.hash(posts, slug, threads, title, user);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Forum {\n");

        sb.append("    posts: ").append(toIndentedString(posts)).append("\n");
        sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
        sb.append("    threads: ").append(toIndentedString(threads)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    user: ").append(toIndentedString(user)).append("\n");
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

