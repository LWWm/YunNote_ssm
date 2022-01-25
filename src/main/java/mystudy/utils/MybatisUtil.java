package mystudy.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
	private static SqlSessionFactory sqlSessionFactory;

	static {
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream("mybatisConfig.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得SqlSession
	 */
	public static SqlSession openSqlSession() throws IOException {
		return sqlSessionFactory.openSession();
	}

	/**
	 * 提交释放资源
	 */
	public static void commitAndClose(SqlSession sqlSession) {
		if (sqlSession != null) {
			sqlSession.commit();
			sqlSession.close();
		}
	}

	/**
	 * 回滚释放资源
	 */
	public static void rollbackAndClose(SqlSession sqlSession) {
		if (sqlSession != null) {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
}
