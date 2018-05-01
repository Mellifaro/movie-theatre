<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <#include "fragments/header.ftl">
<body class="fullBackground">
<div class="container container-bordered">
     <#include "fragments/bodyHeader.ftl">

    <table class="table" id="datatable">
        <thead>
        <tr class="allign-text-center">
            <th width="4%">#</th>
            <th>Event</th>
            <th>Time</th>
            <th>Auditorium</th>
            <th>Seat</th>
            <th>Price</th>
            <th>Discount, %</th>
            <th>Discount type</th>
            <th>Booking time</th>
        </tr>
        </thead>

        <tbody>
                <#list tickets as ticket>
                <tr class="allign-text-center">
                     <td>${ticket?index + 1}</td>
                     <td class="allign-text-left">${ticket.event.name}</td>
                     <td>${ticket.dateTime}</td>
                     <td>${ticket.auditorium.name}</td>
                     <td>${ticket.seat}</td>
                     <td>${ticket.price}</td>
                     <td>${ticket.discount}</td>
                     <td>${ticket.discountType}</td>
                     <td>${ticket.bookingDateTime}</td>
                     <td></td>
                 </tr>
                 </#list>
        </tbody>
    </table>
</div>
<script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
<script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
</body>
</html>

