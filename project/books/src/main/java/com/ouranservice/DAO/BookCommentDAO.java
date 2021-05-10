package com.ouranservice.DAO;

import java.util.List;

import com.ouranservice.entity.BookComment;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface BookCommentDAO {

	//获取全部comment			OK
	List<BookComment> getAllComment();

	//根据书籍Id获取comment		OK
	List<BookComment> getCommentByBookId(int bookId);

	//根据userId获取comment		OK
	List<BookComment> getCommentByUserId(int userId);

	//添加comment				OK
	int addComment(BookComment bookComment);

	//删除comment				OK
	int deleteComment(int commentId);

	//评论禁止修改
	//void updateComment(int commentId);

	//点赞						OK
	int commentLike(int commentId);
}
