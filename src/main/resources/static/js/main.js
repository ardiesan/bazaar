$(document).ready(function() {
    $("#deleteButton").click(function() {
        var $fields = $("#deleteForm").find('input[name="customers"]:checked');

        if (!$fields.length) {
            $('#errorDeleteModal').modal('show');
            return false
        }

        return true;
    });

    $("#deleteButtonNotification").click(function() {
        var $fields = $("#deleteFormNotification").find('input[name="notifications"]:checked');

        if (!$fields.length) {
            $('#errorDeleteModalNotification').modal('show');
            return false
        }

        return true;
    });

    $("#success-alert").delay(4000).slideUp(200, function() {
        console.log("success closing");
        $(this).alert('close');
    });

    $("#failure-alert").delay(4000).slideUp(200, function() {
        console.log("failure closing");
        $(this).alert('close');
    });
});