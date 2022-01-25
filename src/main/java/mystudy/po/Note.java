package mystudy.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Note {
	private Integer noteId; // 云记id
	private String title; // 云记标题
	private String content; // 云记内容
	private Integer typeId; // 云记类型
	private Date pubTime; // 发布时间 ---------- 注意这里的类型 Date

	private Float lon; // 经度
	private Float lat; // 纬度

	private String typeName; // 云记类型名
}
