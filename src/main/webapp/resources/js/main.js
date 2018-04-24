const editIconActive = "/movie/resources/images/edit-active.svg";
const editIconNotActive = "/movie/resources/images/edit-notactive.svg";

$(function(){
    onEditHover();
});

function onEditHover(){
    $(".edit-icon").on({
        "mouseover" : function() {
            this.src = editIconActive;
        },
        "mouseout" : function() {
            this.src = editIconNotActive;
        }
    });
}

