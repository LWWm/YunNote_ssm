package mystudy.mapper;

import mystudy.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

	List<User> findAll();

	User queryUserByName(String userName);

	User queryUserByNickAndUserId(@Param("nick") String nick, @Param("userId") Integer userId);

	int updateUser(User user);
}
