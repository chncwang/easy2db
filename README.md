easy2db
=======

easy2db is to send Java objects to database(only MySQL is supported currently) without writing one line SQL statement.

# Why not use Mybatis?
Mybatis supplies enogh flexibility as it requires you to write SQL statements by hand. But for sending objects to database, simplity is mouch more important than flexibility. Why need we write INSERT and SELECT statements again and again, if the logic can be well abstract?

# Why not use Hibernate?
Hibernate seems too hard to master, so I have no willing to learn it.

# Usage
easy2db is damn easy to use. There are nearly only two things you need do:

0. Define a Java class with table name, primary key, unique key and other columns, using Java annotations.
0. Call *com.chncwang.easy2db.Engine#sendObject*

## Simple example

0. Define the Java class:
```java
@Table("github_repo")
public class GithubRepo {
    @PrimaryKey
    @Column("id")
    private String mId;

    @UniqueKey
    @Column("url")
    private String mUrl;

    @Column("name")
    private String mName;

    @Column("star_count")
    private Integer mStarCount;

    public GithubRepo() {}

    ...
}
```

0. Example object:
```java
public static GithubRepo fool2048() {
    final GithubRepo repo = new GithubRepo();
    repo.setUrl("https://github.com/chncwang/fool2048");
    repo.setName("fool2048");
    repo.setStarCount(1);
    return repo;
}
```

0. Send it!
```java
final Connection connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/github", "wqs", "");
new DefaultEngine(GithubRepo.class, connection).
        sendObjectToDb(GithubRepos.fool2048());
```

## foreign key dependency
After I create a new table named 'user', and add a field named 'user_id' to 'github_repo' table as a foreign key, how shall I send GithubRepo objects now?

It's still damn simple!

0. User class:
```java
@Table("user")
public class User {
    @PrimaryKey
    @Column("id")
    private String mId;

    @UniqueKey
    @Column("user_name")
    private String mUserName;

    public User() {}

    ...
}
```
0. Add 'user_id' field to GithubRepo class:
```java
@Table("github_repo")
public class GithubRepo {

    ...

    @Foreign
    @Column("user_id")
    private User mUser;

    ...
}
```
0. Set User object to GithubRepo object:
```java
public static GithubRepo fool2048() {
    final GithubRepo repo = new GithubRepo();
    ...

    final User user = new User();
    user.setUserName("Chauncey Wang");
    repo.setUser(user);

    return repo;
}
```
0. Send it!
```java
final Connection connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/github", "wqs", "");
new DefaultEngine(GithubRepo.class, connection).
        sendObjectToDb(GithubRepos.fool2048());
```

# How does it work?
0. A target object, with its foreign objects, is sent to database recursivly.
0. When a object is sent, it firstly check whether the object already exists in database. If is, update it, or if isn't, insert it.

# Maven dependency
```xml
<groupId>com.chncwang.easy2db</groupId>
<artifactId>core</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

Currently it's a SNAPSHOT version and hasn't been uploaded to maven central repository. So you may add it to your own nexus server.

# Question?
If you have any question or advices, feel free to send me an email: [chncwang@gmail.com](chncwang@gmail.com).

