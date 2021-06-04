jQuery(document).ready(function () {
  jQuery('.datetimepicker').datepicker({
    timepicker: true,
    language: 'en',
    range: true,
    multipleDates: true,
    multipleDatesSeparator: " - "
  });
  jQuery("#add-event").submit(function () {
    alert("Submitted");
    var values = {};
    $.each($('#add-event').serializeArray(), function (i, field) {
      values[field.name] = field.value;
    });
    console.warn({values, EVENTS})
  });
});

(function () {
  'use strict';
  jQuery(function () {
    // page is ready
    jQuery('#calendar').fullCalendar({
      themeSystem: 'bootstrap4',
      // emphasizes business hours
      businessHours: false,
      defaultView: 'month',
      // event dragging & resizing
      editable: true,
      // header
      header: {
        left: 'title',
        center: '',
        right: 'prev,next'
      },
      events: EVENTS,
      eventClick: function (event) {
        jQuery('.event-title').html(event.title);

        jQuery('.event-body').html(
            "<div class='form-group'><input type='hidden' class='form-control' name='id' value="
            + event.id + "></div>" +
            "<div class='form-group'><label>Start time</label><p>" + new Date(
            event.start).toUTCString() + "</p></div>" +
            "<div class='form-group'><label>End time</label><p>" + new Date(
            event.end).toUTCString() + "</p></div>"
        );

        if (event.active === 'true') {
          jQuery('#addInterviewButton').hide();
        } else {
          jQuery('#addInterviewButton').show();
        }

        jQuery('.event-interview').html(
            "<div class='form-group'><input type='hidden' class='form-control' name='id' value="
            + event.id + "></div>" +
            "<div class='form-group'><input type='hidden' class='form-control' name='startTime' value="
            + event.start + "></div>" +
            "<div class='form-group'><input type='hidden' class='form-control' name='endTime' value="
            + event.end + "></div>" +
            "<div class='form-group'><label>Start time</label><p>" + new Date(
            event.start).toUTCString() + "</p></div>" +
            "<div class='form-group'><label>End time</label><p>" + new Date(
            event.end).toUTCString() + "</p></div>"
        );
        jQuery('.eventUrl').attr('href', event.url);
        jQuery('#modal-view-event').modal();
      },
    })
  });

})(jQuery);
