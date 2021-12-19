$(document).ready(function () {
    $('[data-toggle="table"]').bootstrapTable();
});

function hideAlert() {
    const alert = document.getElementById("alert");
    if (alert != null) {
        alert.style.display = "none";
    }
}

function currencySorter(a, b) {
    const aStripped = Number(a.replace(/(^\$|,|£)/g, ''));
    const bStripped = Number(b.replace(/(^\$|,|£)/g, ''));
    if (aStripped < bStripped) {
        return -1;
    }
    else if (aStripped > bStripped) {
        return 1;
    }
    return -0;
}