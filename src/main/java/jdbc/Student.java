package jdbc;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
class Student {
  Long id;
  String name;
  Long groupId;
}
