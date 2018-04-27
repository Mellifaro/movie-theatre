<#import "spring.ftl" as spring />
<#--<#assign  security=JspTaglibs["http://www.springframework.org/security/tags"] />-->
<#--<#assign  form=JspTaglibs["http://www.springframework.org/tags/form"] />-->

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Movie theatre</title>

        <link rel="stylesheet" href="<@spring.url '/resources/css/style.css'/>"/>
        <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/4.1.0/css/bootstrap.min.css'/>">
    </head>

    <body class="fullBackground">
        <div class="container container-bordered marg">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-item nav-link" href="#">Home</a>
                        <a class="nav-item nav-link" href="#">Features</a>
                        <a class="nav-item nav-link" href="#">Pricing</a>
                        <a class="nav-item nav-link disabled" href="#">Disabled</a>
                    </div>
                </div>

                <form class="navbar-form navbar-right" role="form" action="spring_security_check"
                      method="post">
                    <div class="form-group">
                        <input type="text" placeholder="Email" class="form-control" name='username'>
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="Password" class="form-control" name='password'>
                    </div>
                    <button type="submit" class="btn btn-default">Sign in</button>
                    <a class="btn btn-default" role="button" href="#">Register &raquo;</a>
                    <%--<a href="<c:url value="/j_spring_security_logout" />" >Sign up</a>--%>
                </form>

            </nav>




            <#--<form:form class="navbar-form navbar-right" action="logout" method="post">-->
                <#--<sec:authorize access="isAuthenticated()">-->
                    <#--<%--<sec:authentication var ="username" property="principal.username" />--%>-->
                    <#--<%--receiving username from ModelInterceptor--%>-->
                    <#--<a class="btn btn-default" role="button" href="profile">${userTo.name} <spring:message code="profile"/></a>-->
                    <#--<input type="submit" class="btn btn-default" value=<spring:message code="logout"/>>-->
                <#--</sec:authorize>-->
            <#--</form:form>-->

            <h3><em>The List Of Users</em></h3>
            <table class="table" id="datatable">
                <thead>
                    <tr>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Email</th>
                        <th>Birthday</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>

                <tbody>
                     <#list users as user>
                     <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.birthday}</td>
                        <td><img class="edit-icon" src="<@spring.url '/resources/images/edit-notactive.svg'/>"></td>
                        <td></td>
                     </tr>
                     </#list>
                </tbody>
            </table>

            <form method="POST" action="uploadFile" enctype="multipart/form-data" >
                <input type="file" name="file" />
                <input type="submit" value="Upload">
            </form>
        </div>

        <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/main.js'/>"></script>
    </body>
</html>