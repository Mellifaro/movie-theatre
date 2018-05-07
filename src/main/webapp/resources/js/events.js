$(function(){
    onClickRegister();
});

function onClickRegister() {
    $("#registerButton").on({
        "click" : function() {
            $('#editRow').modal('show');
        }
    });
}

