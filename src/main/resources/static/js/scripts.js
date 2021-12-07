/**
 *
 */

$(document).ready(function () {
    $('.delete-book').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'remove';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Are you sure to remove this book? It can't be undone.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });

    $('.delete-request').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'removeRequest';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Are you sure to remove this request? It can't be undone.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });

    $('.accept-request').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'submitRequest';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Are you sure to accept this request? It can't be undone.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });

    $('.accept-drop').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'start';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Are you sure to start this drop? It can't be undone.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });

    $('.delete-drop').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'removeDrop';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Are you sure to remove this drop? It can't be undone.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });

    $('.roll-drop').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'roll';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Are you sure to roll this drop? It can't be undone.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });


});