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