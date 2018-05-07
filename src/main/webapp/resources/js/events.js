$(function(){
    onClickRegister();
});

function onClickRegister() {
    $("#registerButton").on({
        "click" : function() {
            alert("ss");
            $('#editRow').modal('show');
        }
    });
}

