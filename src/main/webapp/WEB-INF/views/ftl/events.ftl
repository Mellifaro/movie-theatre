<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Movie theatre</title>

        <link rel="stylesheet" href="<@spring.url '/resources/css/style.css'/>"/>
        <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/4.1.0/css/bootstrap.min.css'/>">
    </head>

    <body class="fullBackground">
        <div class="container container-bordered">
            <nav class="navbar navbar-expand-lg navbar-light">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <@security.authorize access="isAuthenticated()">
                            <li class="nav-item"><a class="nav-link" href="#"><strong>My Account</strong></a></li>
                        </@security.authorize>
                            <li class="nav-item"><a class="nav-link" href="#"><strong>Events</a></strong></li>
                        <@security.authorize access="isAuthenticated()">
                            <li class="nav-item"><a class="nav-link" href="#"><strong>Tickets</a></strong></li>
                        </@security.authorize>
                        <@security.authorize access="hasRole('ROLE_ADMIN')">
                            <li class="nav-item"><a class="nav-link" href="#"><strong>Users</a></strong></li>
                            <li class="nav-item"><a class="nav-link" href="#"><strong>Info</a></strong></li>
                        </@security.authorize>
                    </ul>
                    <@security.authorize access="isAnonymous()">
                    <form class="form-inline my-2 my-lg-0" role="form" action="<@spring.url '/events'/>" method="post">
                        <input type="text" class="form-control mr-sm-2" name='username' placeholder="Email" aria-label="Email">
                        <input type="password" class="form-control mr-sm-2" name='password' placeholder="Password" aria-label="Password">
                        <div>
                            <label for="remember-me">Remember me</label>
                            <input type="checkbox" name="remember-me" id="remember-me"/>
                        </div>
                        <button class="btn btn-outline-success my-2 my-sm-0 button-marg type="submit">Sign in</button>
                        <a class="btn btn-outline-success my-2 my-sm-0 button-marg" role="button" href="#">Register &raquo;</a>
                    </form>
                    </@security.authorize>

                    <@security.authorize access="isAuthenticated()">
                        <p class="user-style marg-left-20">Logged as: <strong><@security.authentication property="principal.username"></@security.authentication></strong></p>
                        <p class="user-style marg-left-20">Balance: <strong><@security.authentication property="principal.balance"></@security.authentication> UAH</strong></p>
                        <a href="<@spring.url '/logout'/>">
                            <p class="user-style marg-left-20"><strong>Logout</strong></p>
                        </a>
                    </@security.authorize>
                </div>
            </nav>

            <table class="table" id="datatable">
                <thead>
                <tr>
                    <th width="4%" class="allign-text-center">#</th>
                    <th>Name</th>
                    <th class="allign-text-center">Base price</th>
                    <th class="allign-text-center">Rating</th>
                </tr>
                </thead>

                <tbody>
                     <#list events as event>
                     <tr>
                         <td class="allign-text-center">${event?index + 1}</td>
                         <td>${event.name}</td>
                         <td class="allign-text-center">${event.basePrice}</td>
                         <td class="allign-text-center">${event.rating}</td>
                     </tr>
                     </#list>
                </tbody>
            </table>

            <form method="POST" action="uploadFile" enctype="multipart/form-data" >
                <input type="file" name="file" />
                <input type="submit" value="Upload">
            </form>
        </div>
    </body>
</html>