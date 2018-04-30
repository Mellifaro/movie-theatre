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
                             <td class="allign-text-center">${user?index + 1}</td>
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
    </body>
</html>

