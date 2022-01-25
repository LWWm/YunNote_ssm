package mystudy.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import mystudy.mapper.UserMapper;
import mystudy.po.User;
import mystudy.service.UserService;
import mystudy.vo.ResultInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;


	@Override
	public ResultInfo<User> userLogin(User user) throws IOException {
		ResultInfo<User> resultInfo = new ResultInfo<>();
		resultInfo.setResult(user);

		//判断输入非空
		if (user.getUserName() == null || user.getUserPwd() ==null ) {
			resultInfo.setCode(0);
			resultInfo.setMsg("用户名或密码不能为空！");
			return resultInfo;
		}

		// 用户是否存在，
		// SqlSession sqlSession = MybatisUtil.openSqlSession();
		// UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User userByName = userMapper.queryUserByName(user.getUserName());
		// sqlSession.close();

		if (userByName ==null) {
			resultInfo.setCode(0);
			resultInfo.setMsg("用户名不存在1");
			return resultInfo;
		}

		// 判断密码是否正确
		if (!DigestUtil.md5Hex(user.getUserPwd()).equals(userByName.getUserPwd())) {
			resultInfo.setCode(0);
			resultInfo.setMsg("密码错误");
			return resultInfo;
		}

		resultInfo.setCode(1);
		resultInfo.setResult(userByName);
		return resultInfo;
	}

	@Override
	public Integer checkNick(String nick, Integer userId) throws IOException {
		if (nick ==null)
			return 0;
		User user = userMapper.queryUserByNickAndUserId(nick,userId);
		if (user != null)
			return 0;
		return 1;
	}

	@Override
	public ResultInfo<User> updateUser(HttpServletRequest request) throws Exception {
		ResultInfo<User> resultInfo = new ResultInfo<>();

		User user = (User) request.getSession().getAttribute("user");
		String fileName = null;
		//上传头像
		if (ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory fileItemFactory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
			List<FileItem> list = servletFileUpload.parseRequest(request);
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					switch (fileItem.getFieldName()) {
						case "nick":
							if (fileItem.getString("UTF-8") == null ) {
								resultInfo.setCode(0);
								resultInfo.setMsg("用户昵称不能为空");
								return resultInfo;
							}
							user.setNick(fileItem.getString("UTF-8"));
							break;
						case "mood":
							user.setMood(fileItem.getString("UTF-8"));
							break;
					}
				} else {
					fileName = fileItem.getName();
					if (!"".equals(fileName)){
						String filepath = request.getServletContext().getRealPath("/WEB-INF/upload/");
						fileItem.write(new File(filepath + "/" + fileName));
					}
				}
 			}

		}
		if (!"".equals(fileName))
			user.setHead(fileName);
		int row = userMapper.updateUser(user);
		if (row > 0) {
			resultInfo.setCode(1);
			request.getSession().setAttribute("user", user);
		} else {
			resultInfo.setCode(0);
			resultInfo.setMsg("更新失败!");
		}
		return resultInfo;
	}
}
