package mystudy.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page<T> {
	private Integer pageNum; 		//当前页
	private Integer pageSize;		//每页显示的数量
	private Long totalCount;		//总记录数

	private Integer totalPages;		//总页数
	private Integer prePage;		//上一页
	private Integer nextPage;		//下一页

	private Integer startNavPage;	//导航开始页
	private Integer endNavPage;		//导航结束页

	private List<T> dataList;

	public Page(Integer pageNum,Integer pageSize,Long totalCount) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;

		this.totalPages = (int) Math.ceil(totalCount / (pageSize * 1.0));

		this.prePage = pageNum > 1 ? pageNum - 1 : 1;

		this.nextPage = pageNum >= totalPages ? totalPages : pageNum + 1;

		this.startNavPage = pageNum - 5;
		this.endNavPage = pageSize + 5;
		if (this.startNavPage < 1) {
			this.startNavPage = 1;
			this.endNavPage = Math.min(totalPages, 10);
		}
		if (this.endNavPage > totalPages) {
			this.endNavPage = totalPages;
			this.startNavPage = Math.max(1,this.endNavPage - 9) ;
		}
	}
}
