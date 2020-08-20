package jdbc;

import java.sql.DriverManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

public class JdbcDemo {

  @SneakyThrows
  public static void main(String... args) {
//    @Cleanup val connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "P@ssw0rd!!");
    @Cleanup val connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    @Cleanup val statement = connection.createStatement();

    statement.executeUpdate("create table student (id identity, name varchar not null, group_id int)");
    statement.executeUpdate("insert into student (name, group_id) values ('Вася Пупкин', 123456), ('Федя Прокопов', 654321)");

    @Cleanup val resultSet = statement.executeQuery("select id, name, group_id as groupId from student");
    while (resultSet.next())
      System.out.println(
          Student.builder()
              .id(resultSet.getLong(Student.Fields.id))
              .name(resultSet.getString(Student.Fields.name))
              .groupId(resultSet.getLong(Student.Fields.groupId))
              .build());
  }
}
