$( document ).ready(function() {
    $('[data-toggle="table"]').bootstrapTable();
});

function hideAlert() {
    const alert = document.getElementById("alert");
    if (alert != null) {
        alert.style.display = "none";
    }
}