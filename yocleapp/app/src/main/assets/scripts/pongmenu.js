$(document).ready(function() {
    // show sidebar and overlay
    function showSidebar() {
        sidebar.css('margin-left', '0');

        overlay.show(0, function() {
            overlay.fadeTo('100', 0.0);
        });   
    }

    // hide sidebar and overlay
    function hideSidebar() {
        sidebar.css('margin-left', (sidebar.width() * -1)-10 + 'px');

        overlay.fadeTo('100', 0, function() {
            overlay.hide();
        });
    }

    // selectors
    var sidebar = $('[data-sidebar]');
    var button = $('[data-sidebar-button]');
    var overlay = $('[data-sidebar-overlay]');

    // add height to content area
    overlay.parent().css('min-height', 'inherit');

    // hide sidebar on load
    sidebar.css('margin-left', sidebar.width() * -1 -10 + 'px');

    sidebar.show(0, function() {
        sidebar.css('transition', 'all 100ms ease');
    });

    // toggle sidebar on click
    button.click(function() {
        if (overlay.is(':visible')) {
            hideSidebar();
        } else {
            showSidebar();
        }

        return false;
    });

    // hide sidebar on overlay click
    overlay.click(function() {
        hideSidebar();
    });
});