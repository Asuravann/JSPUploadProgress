<%-- 
    Document   : upload
    Created on : Dec 30, 2016, 1:49:09 PM
    Author     : Indunil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Uploader</title>
    </head>
    <body>
        <form name="form1" id="form1" action="TestServlet" method="post" enctype="multipart/form-data">
	<input type="hidden" name="hiddenfield1" value="ok">
	Files to upload:
	<br/>
	<input type="file" size="50" name="file1">
	<br/>
	<input type="file" size="50" name="file2">
	<br/>
	<input type="file" size="50" name="file3">
	<br/>
	<input type="submit" value="Upload">
	</form>
    </body>
</html>
