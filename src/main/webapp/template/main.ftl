<!DOCTYPE html>
<html lang="en">

<meta http-equiv="content-type" content="text/html;charset=UTF-8" />



<head>
<#include "index-head.ftl">

<style type="text/css">
        
</style>
<script type="text/javascript" src="${contextPath}/static/js/ini/index.js"></script>

</head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<body>

<div class="container">
		 <div class="row">
			 <#include "title.ftl">
		 </div>
		<hr>
     	<div class="row">
			<ul class="nav nav-pills" role="tablist">
				  <li class="active"><a href="#dman_Table"  onclick="winOnload()" data-toggle="tab">教学视频</a></li>
				  <li><a href="#movie_Table" data-toggle="tab">电影</a></li>
				  <li><a href="#book_Table" onclick="winBookloadClick()" data-toggle="tab">书籍</a></li>
				</ul>
		 </div>
		  <hr>
		<div class="tab-content">
			<div class="tab-pane active" id="dman_Table">
				<#include "movieTable.ftl">
                <div id="pagination" class="pagination"></div>
			</div>
			

			
			<div class="tab-pane" id="book_Table">
				<table class="table table-striped" id= "book_tables">
				</table>
				<#--<div id="pagination1" class="pagination"></div>-->
			</div>
			
	     </div>

    </div>  
</body>
<script>
  /*   $("#pagination").whjPaging({
            pageSizeOpt: [
                {'value': 5, 'text': '5条/页', 'selected': true},
                {'value': 10, 'text': '10条/页'},
                {'value': 15, 'text': '15条/页'},
                {'value': 20, 'text': '20条/页'}
            ],
            totalPage: 5,
            showPageNum: 5,
            firstPage: '首页',
            previousPage: '上一页',
            nextPage: '下一页',
            lastPage: '尾页',
            skip: '跳至',
            confirm: '确认',
            refresh: '刷新',
            totalPageText: '共{}页',
            isShowFL: true,
            isShowPageSizeOpt: true,
            isShowSkip: true,
            isShowRefresh: true,
            isShowTotalPage: true,
            isResetPage: false,
            callBack: function (currPage, pageSize) {
                winOnload(currPage, pageSize);
            }
        });
        
          $("#pagination1").whjPaging({
             css: 'css-3',
            totalPage: 18,
            callBack: function (currPage, pageSize) {
               winBookload(currPage, pageSize);
            }
        });
   */
</script>
</html>
