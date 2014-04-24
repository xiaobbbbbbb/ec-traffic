<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>批量数据上传</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/media/css/common/frame.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/media/css/common/add_update.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/media/js/uploadify3.1/uploadify.css" type="text/css" rel="stylesheet" />
        <link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
        <script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js" ></script>
		<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
        <script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
        <script type="text/javascript" src="${ctx}/media/js/uploadify3.1/jquery.uploadify-3.1.js"></script>
        <script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
        <script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
        <script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-business.js"></script>
        <script type="text/javascript">
        $(function(){
        	$("#file_upload").uploadFile({
        		 auto          : false,
        		 fileTypeDesc  : '请选择Excel文件(*.xls)',
                 fileTypeExts  : '*', 
			     uploader      : '${ctx}/upload/uploadFile',
			     onDialogClose : dialogClose,
			     onDialogOpen  : dialogOpen,
			     onUploadSuccess : uploadSuccess
        	});
	    });
        
        function dialogClose(data)
        {
        	$("#fileSize").val(data.queueLength);
        }
        
        function dialogOpen(data)
        {
        	$("#file_upload").uploadify('cancel');
        }
        
        function uploadSuccess(data)
        {
        	$.loadClose();
        	art.dialog.close();
     	    var dataObj=eval(data);
 	    	if(dataObj.msg==100){
 	    	
 	    		art.dialog.alert('上传失败,请检查导入模板是否正确!');
 	    	}
     	    else
     	    {
            	//window.location.href='${ctx}/opeDeviceVersionTemp/list';
     	    	art.dialog.alert("批量成功导入"+dataObj.msg);
     	    }
        }

        function upload()
		{
        	var $fileSize=$("#fileSize").val();
		    if($fileSize>0)
		    {
		    	$("#file_upload").uploadify('upload');
		    	return true;
		    }
		    else
		    {
		    	art.dialog.alert('请选择要上传的文件!');
		    	return false;
		    }
		}
		</script>
	</head>

	<body id="add_update_body">
	    <center>
	        <form id="form" method="post" enctype="multipart/form-data">
	        <input type="hidden" id="fileSize"/>
	        <table width="250px" style="width: 250px;">
	            <tr>
                   <td height="30px"></td>
                </tr>
                <tr>
                   <td valign="bottom" align="left"><input type="file" id="file_upload" name="file_upload" /></td>
                </tr>
                <tr>
                   <td><div id="fileQueue"></div></td>
                </tr>
			</table>
			</form>
         </center>
	</body>
</html>

