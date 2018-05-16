const contextUrl = "/movie";
const deleteIconActive = contextUrl + "/resources/images/delete-active.svg";
const deleteIconNotActive = contextUrl + "/resources/images/delete-notactive.svg";
const addIconActive = contextUrl + "/resources/images/add-active.svg";
const addIconNotActive = contextUrl + "/resources/images/add-notactive.svg";
const deleteURL = "events/delete/";
const deleteDateURL = "events/date/delete";



$(function(){
    onClickRegister();
    onClickAddEvent();
    onRemoveButton();
    onAddButton();
});

function onClickRegister() {
    $("#registerButton").on({
        "click" : function() {
            $('#editRow').modal('show');
        }
    });
}

function onRemoveButton() {
    $(".delete-icon").on({
        "click" : function () {
            let id = $(this).closest('tr').find('td.id').html();
            console.log(id);
            $.ajax({
                url: deleteURL + id,
                method: 'DELETE',
                success: function () {
                    location.reload();
                }
            });
        },
        "mouseover" : function() {
            this.src = deleteIconActive;
        },
        "mouseout" : function() {
            this.src = deleteIconNotActive;
        }
    });
}

function onAddButton() {
    $(".add-icon").on({
        "click" : function() {
            $('#addDate').modal('show');
            let eventId = $(this).closest('tr').find('td.id').html();
            $("#eventId").val(eventId);
        },
        "mouseover" : function() {
            this.src = addIconActive;
        },
        "mouseout" : function() {
            this.src = addIconNotActive;
        }
    });
}

function onClickAddEvent(){
    $("#createEvent").on({
        "click" : function() {
            $('#addEvent').modal('show');
        }
    });
}
