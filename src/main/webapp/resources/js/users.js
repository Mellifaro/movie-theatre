const deleteIconActive = "/movie/resources/images/delete-active.svg";
const deleteIconNotActive = "/movie/resources/images/delete-notactive.svg";
const deleteURL = "users/delete/";

$(function(){
    onRemoveButton();
});

function onRemoveButton(){
    $(".delete-icon").on({
        "click" : function () {
            let id = $(this).closest('tr').find('td.id').html();
            console.log(id);
            $.ajax({
                url: deleteURL + id,
                method: 'POST',
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

