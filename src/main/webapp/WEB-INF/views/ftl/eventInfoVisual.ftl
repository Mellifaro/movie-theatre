<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <#include "fragments/header.ftl">
    <body class="fullBackground">
        <div id="container" class="container container-bordered">
         <#include "fragments/bodyHeader.ftl">
            <hr>

            <div id="cinema-hall" class="account">
                <div class="auditorium-title">
                    <p><h5>Auditorium: <strong>${auditoriumDTO.name}</strong></h5></p>
                </div>
                <div>
                    <table>
                        <tbody>
                            <#list auditoriumDTO.seatMatrix as raw>
                            <tr>
                                <#list raw as seat>
                                    <#if seat.currentStatus.toString() == "PURCHASED">
                                        <td><div class="seat purchased">${seat.seat}</div></td>
                                    <#elseif seat.currentStatus.toString() == "VIP">
                                        <td><div class="seat vip">${seat.seat}</div></td>
                                    <#else >
                                        <td><div class="seat">${seat.seat}</div></td>
                                    </#if>
                                </#list>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="seat-info" class="seat-info">
                <table>
                    <tbody>
                    <tr>
                        <td><div class="seat"></div></td>
                        <td><div class="seat-text-info">-</div></td>
                        <td><div class="seat-text-info">Free seat</div></td>
                    </tr>
                    <tr>
                        <td><div class="seat vip"></div></td>
                        <td><div class="seat-text-info">-</div></td>
                        <td><div class="seat-text-info">Free VIP seat</div></td>
                    </tr>
                    <tr>
                        <td><div class="seat purchased"></div></td>
                        <td><div class="seat-text-info">-</div></td>
                        <td><div class="seat-text-info">Purchased seat</div></td>
                    </tr>

                    <tr>
                        <td><div id="select-info" class="seat selected"></div></td>
                        <td><div class="seat-text-info">-</div></td>
                        <td><div class="seat-text-info">Selected seat</div></td>
                    </tr>
                    </tbody>
                </table>
                <div class="book-button">
                    <button class="btn btn-outline-primary my-2 my-sm-0" id="bookTicketsButton">Book tickets</button>
                     <@security.authorize access="hasRole('ROLE_ADMIN')">
                        <button class="btn btn-outline-danger my-2 my-sm-0" id="removeDateButton">Remove date</button>
                     </@security.authorize>
                </div>
            </div>
            <div align="right" id="event-info" class="event-info">
                <p><span id="eventId" class="hidden">${event.id}</span>Movie: <strong>${event.name}</strong></p>
                <p>Date: <strong><span id="datetime">${eventTime}</span></strong></p>
                <p>Base price: <strong><span id="datetime">${event.basePrice} UAH</span></strong></p>
            </div>
            <div id="back-href" class="back-href">
                <a href="<@spring.url '/events/${event.id}/date/${eventTime}'/>">Simple interface</a>
            </div>
        </div>

        <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/cinema.js'/>"></script>

</body>
</html>