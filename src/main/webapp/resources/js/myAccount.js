const putMoneyUrl = "account/money/put";
const withdrawMoneyUrl = "account/money/withdraw";

$(function(){
    onPutButton();
    onWithdrawButton();
});


function onPutButton(){
    $("#putMoneyButton").on({
        "click" : function () {
            let amount = $("#accountUAH").val();
            let email = $("#userEmail").html();
            let putMoney = {
                amount: amount,
                email: email
            };
            $.ajax({
                url: putMoneyUrl,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: 'POST',
                data: JSON.stringify(putMoney),
                success: function () {
                    location.reload();
                },
                error: function () {
                    $("#account").append("<p id=\"errorMessage\" class=\"red-color marg-left-20\">Operation with balance was not successful</p>");
                }
            });
        }
    });
}

function onWithdrawButton(){
    $("#withdrawMoneyButton").on({
        "click" : function () {
            let amount = $("#accountUAH").val();
            let email = $("#userEmail").html();
            let withdrawMoney = {
                amount: amount,
                email: email
            };
            $.ajax({
                url: withdrawMoneyUrl,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: 'POST',
                data: JSON.stringify(withdrawMoney),
                success: function () {
                    location.reload();
                },
                error: function () {
                    $("#account").append("<p id=\"errorMessage\" class=\"red-color marg-left-20\">Operation was not successful</p>");
                }
            });
        }
    });
}
