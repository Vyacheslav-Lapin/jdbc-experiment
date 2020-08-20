package jdbc;

import java.sql.DriverManager;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

public class JdbcDemoPreparedStatement {

  @SneakyThrows
  public static void main(String... args) {
//    @Cleanup val connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "P@ssw0rd!!");
    @Cleanup val connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    @Cleanup val statement = connection.createStatement();

    statement.executeUpdate("create table student (id identity, name varchar not null, group_id int)");
    statement.executeUpdate("insert into student (name, group_id) values ('Вася Пупкин', 123456), ('Федя Прокопов', 654321)");

    @Cleanup val preparedStatement = connection.prepareStatement("select id, name, group_id as groupId from student where id = ?");
    preparedStatement.setLong(1, 1L);
    @Cleanup val resultSet = preparedStatement.executeQuery();
    while (resultSet.next())
      System.out.println(
          Student.builder()
              .id(resultSet.getLong(Student.Fields.id))
              .name(resultSet.getString(Student.Fields.name))
              .groupId(resultSet.getLong(Student.Fields.groupId))
              .build());

    preparedStatement.setLong(1, 2L);
    @Cleanup val resultSet2 = preparedStatement.executeQuery();
    while (resultSet2.next())
      System.out.println(
          Student.builder()
              .id(resultSet2.getLong(Student.Fields.id))
              .name(resultSet2.getString(Student.Fields.name))
              .groupId(resultSet2.getLong(Student.Fields.groupId))
              .build());
  }
}
