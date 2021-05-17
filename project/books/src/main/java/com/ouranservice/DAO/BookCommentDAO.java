package com.ouranservice.DAO;

import java.util.List;

import com.ouranservice.entity.BookComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BookCommentDAO {

	//获取全部comment			OK
	List<BookComment> getAllComment();

	//根据书籍Id获取comment		OK
	List<BookComment> getCommentByBookId(@Param("bookId")int bookId, @Param("pageNumber")int pageNumber);

	//根据userId获取comment		OK
	List<BookComment> getCommentByUserId(@Param("userId")int userId, @Param("pageNumber")int pageNumber);

	//添加comment				OK
	int addComment(BookComment bookComment);

	//删除comment				OK
	int deleteComment(int commentId);

	//评论禁止修改
	//void updateComment(int commentId);

	//点赞						OK
	int commentLike(int commentId);
}
