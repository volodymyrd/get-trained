package online.gettrained.backend.json_serializers.so_dates;

import java.util.Date;
import online.gettrained.backend.json_serializers.SelectOptionDateJsonSerializer;
import online.gettrained.backend.utils.DateUtils;

/**
 * Implementation of {@link SelectOptionDateJsonSerializer} for {@link DateUtils#LONG_DATE_FORMAT}.
 */
public class SelectOptionLongDateJsonSerializer extends SelectOptionDateJsonSerializer {

  @Override
  protected String convertDateToString(Date date) {
    return DateUtils.getLongDateFormat().format(date);
  }
}
