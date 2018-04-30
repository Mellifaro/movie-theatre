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
                    <th>Name</th>
                    <th>Date-Audinorium</th>
                    <th class="allign-text-center">Base price</th>
                    <th class="allign-text-center">Audinorium</th>
                    <th class="allign-text-center">Rating</th>
                </tr>
                </thead>

                <tbody>
                     <#list events as event>
                     <tr>
                         <td class="allign-text-center">${event?index + 1}</td>
                         <td>${event.name}</td>
                         <td>
                             <#list event.auditoriums as date, auditorium>
                                 <a href="<@spring.url '/events/${event.id}/date/${date}'/>">
                                     ${date} - ${auditorium.name}<br>
                                 </a>
                             </#list>

                         </td>
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