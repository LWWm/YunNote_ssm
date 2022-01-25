package mystudy;

import mystudy.mapper.NoteMapper;
import mystudy.mapper.UserMapper;
import mystudy.po.NoteType;
import mystudy.po.User;
import mystudy.service.UserService;
import mystudy.utils.MybatisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ssmTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private NoteMapper noteMapper;

	@Test
	public void test() throws IOException {
		UserMapper mapper = MybatisUtil.openSqlSession().getMapper(UserMapper.class);
		System.out.println(mapper.getClass());
		List<User> all = mapper.findAll();
		System.out.println(all);
	}


	@Test
	public void testSSM() {
		List<User> all = userMapper.findAll();
		System.out.println(all);
	}

	@Test
	public void testSSM2() {
		List<NoteType> typeListByUserId = noteMapper.findTypeListByUserId(1);
		System.out.println(typeListByUserId);
	}
}
