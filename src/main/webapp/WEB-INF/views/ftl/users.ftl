<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <#include "fragments/header.ftl">
    <body class="fullBackground">
        <div class="container container-bordered">
                <#include "fragments/bodyHeader.ftl">

                <table class="table" id="datatable">
                    <thead>
                    <tr>
                        <th width="4%" class="allign-text-center">#</th>
                        <th class="d-none">Id</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Email</th>
                        <th>Birthday</th>
                        <#--<th>Edit</th>-->
                        <th class="allign-text-center">Remove</th>
                    </tr>
                    </thead>

                    <tbody>
                         <#list users as user>
                         <tr>
                             <td class="allign-text-center">${user?index + 1}</td>
                             <td class="id d-none">${user.id}</td>
                             <td>${user.firstName}</td>
                             <td>${user.lastName}</td>
                             <td class="email">${user.email}</td>
                             <td>${user.birthday}</td>
                             <#--<td><img class="edit-icon" src="<@spring.url '/resources/images/edit-notactive.svg'/>"></td>-->
                             <td align="center"><img class="delete-icon" src="<@spring.url '/resources/images/delete-notactive.svg'/>"></td>
                         </tr>
                         </#list>
                     </tbody>
                </table>

            <form method="POST" action="<@spring.url '/users/uploadFile'/>" enctype="multipart/form-data" >
                <input type="file" name="file" />
                <input type="submit" value="Upload">
            </form>
        </div>

        <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/users.js'/>"></script>
    </body>
</html>

