package mystudy.service;

import mystudy.po.User;
import mystudy.vo.ResultInfo;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface UserService {

	/**
	 * 1. 判断参数是否为空
	 * 如果为空
	 * 设置ResultInfo对象的状态码和提示信息
	 * 返回resultInfo对象
	 * 2. 如果不为空，通过用户名查询用户对象
	 * 3. 判断用户对象是否为空
	 * 如果为空
	 * 设置ResultInfo对象的状态码和提示信息
	 * 返回resultInfo对象
	 * 4. 如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
	 * 如果密码不正确
	 * 设置ResultInfo对象的状态码和提示信息
	 * 返回resultInfo对象
	 * 5. 如果密码正确
	 * 设置ResultInfo对象的状态码和提示信息
	 * 6. 返回resultInfo对象
	 *
	 */
	ResultInfo<User> userLogin(User user) throws IOException;

	/**
	 * 验证昵称的唯一性：
	 * --1.判断昵称是否为空
	 * ----如果为空，返回"0"（使用字符串）
	 * --2.调用Dao层，通过用户ID和昵称查询用户对象
	 * --3.判断用户对象存在
	 * ----存在，返回"0"（输入的昵称不可用，因为已有）
	 * ----不存在，返回"1"（输入的昵称可用）
	 *
	 * @param nick
	 * @param userId
	 * @return
	 */
	Integer checkNick(String nick, Integer userId) throws IOException;

	ResultInfo<User> updateUser(HttpServletRequest request) throws Exception;
}
