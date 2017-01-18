<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>

<html>
<head>
    <title>Login</title>
</head>

<body onload='document.loginForm.username.focus();'>

<div id="login-box">
    <form name='loginForm'
          action="<c:url value='/login'/>" method='POST'>
        <table>
            <tr>
                <td>User:</td>
                <td><input type='text' name='username'></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password'/></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit"
                                       value="submit"/></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>