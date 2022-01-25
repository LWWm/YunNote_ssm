package mystudy.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 封装返回结果的类：
 * 	状态码：	0：成功，	1：失败
 * 	提示信息
 * 	返回的对象（字符串，javabean，集合，map等）
 */

@Getter
@Setter
public class ResultInfo<T> {

	private Integer code;
	private String msg;
	private T result;

}
