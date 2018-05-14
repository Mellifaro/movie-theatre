<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <#include "fragments/header.ftl">
    <body class="fullBackground">
    <div class="container container-bordered">
        <#include "fragments/bodyHeader.ftl">

        <div id="account" class="account">
            <p class="user-style marg-left-20">Account: <strong id="userEmail"><@security.authentication property="principal.username"></@security.authentication></strong></p>
            <p class="user-style marg-left-20">Balance: <strong><@security.authentication property="principal.balance"></@security.authentication> UAH</strong></p>
            <div class="col-12 pad-bottom-20">
                <input type="number" step="0.01" class="form-control marg-15" id="accountUAH" name="accountUAH" placeholder="0.00">
            </div>
            <button class="btn btn-outline-primary my-2 my-sm-0 user-style marg-20" id="putMoneyButton">Put</button>
            <button class="btn btn-outline-danger my-2 my-sm-0 user-style" id="withdrawMoneyButton">Withdraw</button>
        </div>

        <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/myAccount.js'/>"></script>
    </div>
    </body>
</html>