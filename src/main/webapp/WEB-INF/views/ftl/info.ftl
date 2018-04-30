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
                <th>Discount type</th>
                <th class="allign-text-center">Amount</th>
            </tr>
            </thead>

            <tbody>
                         <#list info as propName, propValue>
                         <tr>
                             <td class="allign-text-center">${propName?index + 1}</td>
                             <td>${propName}</td>
                             <td class="allign-text-center">${propValue}</td>
                         </tr>
                         </#list>
            </tbody>
        </table>

    <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
</div>
</body>
</html>