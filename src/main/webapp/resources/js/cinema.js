const bookUrl = "/movie/book/";
const eventsUrl = "/movie/events";
const deleteDateURL = "/movie/events/date/delete";

$(function(){
    onClickSeat();
    onBookButton();
    onRemoveDateButton();
});

function onClickSeat(){
    $(".seat").not(".purchased").on({
        "click" : function() {
            let seat = $(this);
            if(seat.hasClass("selected") === true){
                seat.removeClass("selected");
            }else{
                seat.addClass("selected");
            }
        }
    })
}

function onBookButton(){
    $("#bookTicketsButton").on({
        "click" : function() {
            let selectedTickets = $(".selected").not("#select-info");
            let seats = [];
            selectedTickets.each(function(){
                seats.push($(this).html());
            });
            let eventId = $("#eventId").html();
            let datetime = $("#datetime").html();
            let bookingInfo = {
                eventId: eventId,
                dateTime: datetime,
                seats: seats
            };
            console.log(eventId);
            console.log(datetime);
            console.log(seats);
            console.log(window.location.pathname);
            console.log(window.location.href);

            $.ajax({
                url: bookUrl,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: 'POST',
                data: JSON.stringify(bookingInfo),
                success: function () {
                    location.reload();
                },
                error: function () {
                    $(".container").append("<p id=\"errorMessage\" class=\"red-color marg-left-20\">Booking operation was not successful</p>");
                }
            });
        }
    })
}

function onRemoveDateButton() {
    $("#removeDateButton").on({
        "click" : function () {
            let eventTime = $("#datetime").html();
            let eventId = $("#eventId").html();
            console.log(eventTime);
            console.log(eventId);
            $.ajax({
                url: deleteDateURL,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data:JSON.stringify({
                    eventId: eventId,
                    eventTime: eventTime,
                }),
                method: 'POST',
                success: function () {
                    location.href = window.location.protocol + "//" + window.location.host + eventsUrl;
                }
            });
        }
    });
}
