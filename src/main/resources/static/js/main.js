$("#deleteButton").click(function() {
    var $fields = $("#deleteForm").find('input[name="customers"]:checked');

    if (!$fields.length) {
        $('#errorDeleteModal').modal('show');
        return false
    }

    return true;
});