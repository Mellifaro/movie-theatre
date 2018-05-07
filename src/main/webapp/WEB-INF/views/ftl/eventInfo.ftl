<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<html>
    <#include "fragments/header.ftl">
    <body class="fullBackground">
        <div class="container container-bordered">
            <#include "fragments/bodyHeader.ftl">

            <div class="eventBooking">
                <p class="user-style marg-left-20"><strong>${event.name}</strong></p>
                <p class="user-style marg-left-20"><strong>${eventTime} - Auditorium: ${auditorium.name}</strong></p><br>
                <p class="user-style marg-left-20">Available seats:</p>

                <p class="user-style marg-left-20">Simple seats: <strong>
                    <#list simpleSeats as simpleSeat>
                        ${simpleSeat}<#if simpleSeat_has_next>,</#if>
                    </#list>
                </strong></p>

                <p class="user-style marg-left-20">VIP seats: <strong>
                    <#list vipSeats as vipSeat>
                         ${vipSeat}<#if vipSeat_has_next>,</#if>
                    </#list>
                </strong></p>


                    <form action="<@spring.url '/book/event/${event.id}/datetime/${eventTime}'/>" method="post">
                        <div class="col-12 pad-bottom-20">
                            <label for="bookTickets" class="user-style">Enter seats separated by commas</label>
                            <input type="text" class="form-control marg-15 " id="seats" name="seats" placeholder="Seats">
                        </div>
                        <button type="submit" class="btn btn-outline-primary my-2 my-sm-0 user-style marg-left-20" id="buyTickets">Book
                            tickets
                        </button>
                    </form>
                </div>
            </div>

            <script type="text/javascript" src="<@spring.url '/webjars/jquery/3.3.1-1/jquery.min.js'/>"></script>
            <script type="text/javascript" src="<@spring.url '/webjars/bootstrap/4.1.0/js/bootstrap.min.js'/>"></script>
            <script type="text/javascript" src="<@spring.url '/resources/js/myAccount.js'/>"></script>
        </div>

    </body>
</html>