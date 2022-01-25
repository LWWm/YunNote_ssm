package mystudy.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class User {
	private Integer userId;
	private String userName;
	private String userPwd;
	private String nick;
	private String head;
	private String mood;
}
