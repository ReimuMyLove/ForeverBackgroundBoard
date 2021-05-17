#方法及接口备注

##book-1 找书(简略)
###作用:         
根据 bookId 查询对应书籍信息(不包括评论信息)
###接口网址:      
/ouranbook/book/briefById
###接口参数:      
?bookId=#{bookId}
###返回数据:
Book.class

##book-2 找书(详细)
###作用:          
根据 bookId 查询对应书籍信息(包括评论信息)
###接口网址:       
/ouranbook/book/detailById
###接口参数:       
?bookId=#{bookId}
###返回数据:
Book.class

##book-3 找书(全部)
###作用:
获取全部书籍
###接口网址:
/ouranbook/book/getAll
###接口参数:
无
###返回数据:
List<Book>

##book-4 筛选书籍
###作用:
根据筛选条件查询书籍
###接口网址:
/ouranbook/book/byRequest
###接口参数: (所有参数必须写 即使没有数据也需要写参数头)
###例: typeName=惊悚&area=&year=2021&month= 
?type=#{typeName}&area=#{area}&year=#{year}&month= #{month}
###返回数据:
List<Book>

##type-1 查询分类
###作用:          
根据 typeName 查询对应类型信息(包括此类型全部书籍的简略信息)
###接口网址:       
/ouranbook/types/byTypeName
###接口参数:       
?typeName=#{typeName}
###返回数据:
BookTypes.class

##comment-1 查看个人书评
###作用:          
根据 userId 查询对应评论信息
###接口网址:       
/ouranbook/comment/byUserId
###接口参数:       
?userId=#{userId}
###返回数据:
BookComment.class

##comment-2 查看指定书评
###作用:
根据 bookId 查询对应评论信息
###接口网址:
/ouranbook/comment/byBookId
###接口参数:
?bookId=#{bookId}
###返回数据:
BookComment.class

##comment-3 点赞书评
###作用:    
根据 commentId 增加对应评论的点赞数
###接口网址:
/ouranbook/comment/commentLike
###接口参数:
?commentId=#{commentId}
###返回数据:
true/false

##comment-4 发表书评
###作用:
将 comment 对象上传至数据库
###接口网址:
/ouranbook/comment/add
###接口参数:
?comment=#{comment}
###返回数据:
true/false



##comment-5 删除书评
###作用:
根据 commentId 删除书评
###接口网址:
/ouranbook/comment/delete
###接口参数:
?commentId=#{commentId}
###返回数据:
true/false
