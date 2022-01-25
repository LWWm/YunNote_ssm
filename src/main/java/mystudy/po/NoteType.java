package mystudy.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoteType {
	private Integer typeId;
	private String typeName;
	private Integer userId;
}
