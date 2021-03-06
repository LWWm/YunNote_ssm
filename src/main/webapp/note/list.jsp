
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 引入格式化的标签库，这里用来格式化日期 --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- index.jsp 页面的右侧内容 -->
<div class="col-md-9">
    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
            云记列表
        </div>

        <%-- 判断云记列表是否存在 --%>
        <%-- 不存在时 --%>
        <c:if test="${empty page}">
            <h2>暂未查询到云记记录！</h2>
        </c:if>

        <%-- 存在时 --%>
        <c:if test="${!empty page}">
            <%-- 遍历获取云记列表 --%>
            <div class="note_datas">
                <ul>
                        <%-- <li>『 2016-08-04』&nbsp;&nbsp;<a href="note?act=view&amp;noteId=27">我们</a></li> --%>
                    <c:forEach items="${page.dataList}" var="item">
                        <li>
                            『<fmt:formatDate value="${item.pubTime}" pattern="yyyy-MM-dd"/>』&nbsp;&nbsp;
                            <a href="note/detail?noteId=${item.noteId}">${item.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <%-- 设置分页导航 --%>
            <nav style="text-align: center">
                <ul class="pagination   center">
                        <%-- 上一页 --%>
                        <%-- 如果当前不是第一页，则显示上一页的按钮 --%>
                    <c:if test="${page.pageNum > 1}">
                        <li>
                            <a href="index/${action}?pageNum=${page.prePage}&title=${title}&date=${date}&typeId=${typeId}">
                                <span>«</span>
                            </a>
                        </li>
                    </c:if>

                        <%-- 分页导航页码 --%>
                    <c:forEach begin="${page.startNavPage}" end="${page.endNavPage}" var="p">
                        <li <c:if test="${page.pageNum == p}">class="active" </c:if>>
                            <a href="index/${action}?pageNum=${p}&title=${title}&date=${date}&typeId=${typeId}">${p}</a>
                        </li>
                    </c:forEach>

                        <%-- 下一页 --%>
                        <%-- 如果当前不是最后一页，则显示下一页的按钮 --%>
                    <c:if test="${page.pageNum < page.totalPages}">
                        <li>
                            <a href="index/${action}?pageNum=${page.nextPage}&title=${title}&date=${date}&typeId=${typeId}">
                                <span>»</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
    </div>
</div>